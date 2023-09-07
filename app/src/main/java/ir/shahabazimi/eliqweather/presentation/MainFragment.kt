package ir.shahabazimi.eliqweather.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import eliqweather.data.utils.convertToReadableDate
import eliqweather.data.utils.getHourOfDay
import eliqweather.data.utils.visibilityState
import ir.shahabazimi.eliqweather.R
import ir.shahabazimi.eliqweather.adapter.WeatherRecyclerViewAdapter
import ir.shahabazimi.eliqweather.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale


/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var adapter: WeatherRecyclerViewAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) getLocation()
        }

    private var locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permission ->
        if (permission)
            if (isLocationServiceEnabled()) getLocation()
            else requestLocationServices()
        else
            Toast.makeText(context, R.string.location_permission, Toast.LENGTH_SHORT).show()

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

    private fun handleLocationPermission() {
        binding.refreshLayout.isRefreshing = false
        if (locationPermissionGranted())
            if (isLocationServiceEnabled())
                getLocation()
            else
                requestLocationServices()
        else
            locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun locationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    @SuppressLint("MissingPermission")
    private fun getLocation() =
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    handleLocation(location)
                } ?: viewModel.getWeatherInfo()
            }


    private fun handleLocation(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geocoder.getFromLocation(latitude, longitude, 1)?.first()
        if (address != null)
            viewModel.address = address.locality

        viewModel.setLocation(latitude, longitude)
    }

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

    private fun observeWeatherLoading() =
        viewModel.responseLoading.observe(viewLifecycleOwner) { loading ->
            with(binding) {
                errorView.visibilityState(false)
                loadingView.visibilityState(loading)
                refreshLayout.isRefreshing = false
            }
        }

    private fun observeWeatherResponse() =
        viewModel.response.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                with(binding) {
                    locationText.text = response.timezone
                    temperatureText.text = getString(
                        R.string.temperature_format,
                        response.hourlyWeather[getHourOfDay()].temperature.toString()
                    )
                    dateText.text = response.dailyWeather.first().date.convertToReadableDate()
                    weatherConditionText.text = getString(response.dailyWeather.first().weatherCode)
                    weatherIcon.setAnimation(response.dailyWeather.first().weatherIcon)
                    weatherIcon.playAnimation()
                    adapter.setData(response.dailyWeather)
                }
            }
        }

    private fun isLocationServiceEnabled(): Boolean {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestLocationServices() {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 100000)
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest.build())
        LocationServices.getSettingsClient(requireContext()).checkLocationSettings(builder.build())
            .addOnSuccessListener {
                getLocation()
            }.addOnFailureListener { exception ->
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
        locationPermissionRequest.unregister()
        resolutionForResult.unregister()
    }

}