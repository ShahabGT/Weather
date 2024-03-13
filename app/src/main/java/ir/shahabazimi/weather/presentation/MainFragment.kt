package ir.shahabazimi.weather.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import ir.shahabazimi.weather.R
import ir.shahabazimi.weather.adapter.WeatherRecyclerViewAdapter
import ir.shahabazimi.weather.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import weather.data.utils.convertToReadableDate
import weather.data.utils.getHourOfDay
import weather.data.utils.ifNullOrEmpty
import weather.data.utils.orZero
import weather.data.utils.roundToNearestInt
import weather.data.utils.visibilityState
import java.util.Locale


/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
class MainFragment : BaseFragment<FragmentMainBinding>() {

    //initializing view model with koin
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var adapter: WeatherRecyclerViewAdapter

    //location client for getting users location (lat,lon)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //callback when users allows or denies device gps access
    private var resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) getLocation()
        }

    //callback for accessing location
    private var locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permission ->
        //if location is granted and device location is enabled it will get users location
        //if location is granted and device location is disabled it will ask for user to enables device gps
        //else if location is not granted a toast will shown
        if (permission)
            if (isLocationServiceEnabled()) getLocation()
            else requestLocationServices()
        else
            Toast.makeText(context, R.string.location_permission, Toast.LENGTH_SHORT).show()

    }

    // priority balances is used because accurate location is not used
    // interval is can be omitted because it is a one time location call
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100000)
    private val builder =
        LocationSettingsRequest.Builder().addLocationRequest(locationRequest.build())

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (location != null) {
                    handleLocation(location)
                }
            }
        }
    }

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)


    override fun initLogic() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        observeWeatherError()
        observeWeatherLoading()
        observeWeatherResponse()
        viewModel.getWeatherInfo()

    }

    override fun setupViews(savedInstanceState: Bundle?) = with(binding) {
        adapter = WeatherRecyclerViewAdapter()
        dailyWeatherRecycler.adapter = adapter
        refreshLayout.setOnRefreshListener {
            handleLocationPermission()
        }
    }

    //this function is used by swipe refresh layout and checks if location and gps is enabled else asks for permission
    private fun handleLocationPermission() {
        binding.refreshLayout.isRefreshing = false
        if (locationPermissionGranted())
            if (isLocationServiceEnabled())
                getLocation()
            else
                requestLocationServices()
        else
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    //checks whether location access is granted or not
    private fun locationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    //gets users location
    @SuppressLint("MissingPermission")
    private fun getLocation() =
        fusedLocationClient.lastLocation
            // used elvis operator when the location is null gets the default weather data
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    handleLocation(location)
                } ?: startLocationRequest()
            }

    @SuppressLint("MissingPermission")
    private fun startLocationRequest() {
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(locationRequest.build(), mLocationCallback, null)
    }

    //handles the location address provided by fusedLocationClient
    @Suppress("DEPRECATION")
    private fun handleLocation(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        //used try catch because getFromLocation throws exception, in that case getting address is ignored
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            //getFromLocation is deprecated in SDK 33, hence both implementation is used for SDK blow and above 33
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    addresses.first()?.let {
                        viewModel.address = it.thoroughfare.ifNullOrEmpty(it.locality)
                    }
                    viewModel.setLocation(latitude, longitude)
                }
            } else {
                val address = geocoder.getFromLocation(latitude, longitude, 1)?.first()
                if (address?.locality != null)
                    viewModel.address = address.locality
                viewModel.setLocation(latitude, longitude)
            }
        } catch (e: Exception) {
            viewModel.setLocation(latitude, longitude)
        }

    }

    //function for observing api call errors, simply shows an error layout
    private fun observeWeatherError() =
        viewModel.responseError.observe(viewLifecycleOwner) { error ->
            with(binding) {
                errorView.visibilityState(true)
                errorText.text = error
                retryButton.setOnClickListener {
                    viewModel.getWeatherInfo()
                }
            }

        }

    //function for observing api call loading, simply shows loading layout
    private fun observeWeatherLoading() =
        viewModel.responseLoading.observe(viewLifecycleOwner) { loading ->
            with(binding) {
                errorView.visibilityState(false)
                loadingView.visibilityState(loading)
                refreshLayout.isRefreshing = false
            }
        }

    // created the layout based on the response from api (both online and offline) calls
    private fun observeWeatherResponse() = with(binding) {
        viewModel.response.observe(viewLifecycleOwner) { response ->
            if (response != null && response.latitude.orZero().toInt() != 0) {
                locationText.text = response.timezone
                temperatureText.text = getString(
                    R.string.temperature_format,
                    response.hourlyWeather[getHourOfDay()].temperature.roundToNearestInt()
                )
                dateText.text = response.dailyWeather.first().date.convertToReadableDate()
                weatherConditionText.text = getString(response.dailyWeather.first().weatherCode)
                weatherIcon.setAnimation(response.dailyWeather.first().weatherIcon)
                weatherIcon.playAnimation()
                adapter.setData(response.dailyWeather)

            } else {
                errorView.visibilityState(true)
                retryButton.visibilityState(false)
                loadingView.visibilityState(false)
                refreshLayout.isRefreshing = false
            }
        }
    }

    //checks whether the device gps is enabled or not
    private fun isLocationServiceEnabled(): Boolean {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    //this function is used to ask the user to enabled the device gps
    private fun requestLocationServices() {
        LocationServices.getSettingsClient(requireContext()).checkLocationSettings(builder.build())
            .addOnSuccessListener {
                //this will be called when used enables the gps
                getLocation()
            }.addOnFailureListener { exception ->
                // this block is called when the device gps is turned off and aks for it to be enabled
                if (exception is ResolvableApiException) {
                    try {
                        resolutionForResult.launch(
                            IntentSenderRequest
                                .Builder(exception.resolution).build()
                        )
                    } catch (_: Throwable) {
                    }
                }
            }
    }


    override fun onDestroy() {
        super.onDestroy()
        //removing the gps and location call back when fragment is destroyed
        locationPermissionRequest.unregister()
        resolutionForResult.unregister()
    }

}