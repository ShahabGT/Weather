package ir.shahabazimi.eliqweather.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import eliqweather.data.utils.convertToReadableDate
import eliqweather.data.utils.getHourOfDay
import eliqweather.data.utils.visibilityState
import ir.shahabazimi.eliqweather.R
import ir.shahabazimi.eliqweather.WeatherViewModel
import ir.shahabazimi.eliqweather.adapter.WeatherRecyclerViewAdapter
import ir.shahabazimi.eliqweather.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var adapter: WeatherRecyclerViewAdapter
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissions ->
        if (permissions) {
            //todo handle permission
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initViews()
    }


    private fun init() {
        viewModel.getWeatherInfo(isOnline = true)
        observeWeatherError()
        observeWeatherLoading()
        observeWeatherResponse()
    }

    private fun initViews() = with(binding) {
        adapter = WeatherRecyclerViewAdapter()
        dailyWeatherRecycler.adapter = adapter
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                locationFloatingButton.hide()
            } else {
                locationFloatingButton.show()
            }
        })

        locationFloatingButton.setOnClickListener {
            if (!isLocationPermissionGranted())
                locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            else
                Toast.makeText(context, "granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


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
            }
        }

    private fun observeWeatherResponse() =
        viewModel.response.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                with(binding) {
                    timeZoneText.text = response.timezone
                    temperatureText.text = getString(
                        R.string.temperature_format,
                        response.hourlyWeather[getHourOfDay()].temperature.toString()
                    )
                    dateText.text = response.dailyWeather.first().date.convertToReadableDate()
                    weatherConditionText.text = getString(response.dailyWeather.first().weatherCode)
                    weatherIcon.setAnimation(response.dailyWeather.first().weatherIcon)
                    adapter.setData(response.dailyWeather)

                }
            }
        }

    private fun openSettingsPage() {
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply { data = uri })
    }


}