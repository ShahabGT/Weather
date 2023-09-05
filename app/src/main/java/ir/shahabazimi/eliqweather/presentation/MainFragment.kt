package ir.shahabazimi.eliqweather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eliqweather.data.utils.convertToReadableDate
import eliqweather.data.utils.getHourOfDay
import ir.shahabazimi.eliqweather.R
import ir.shahabazimi.eliqweather.WeatherViewModel
import ir.shahabazimi.eliqweather.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: WeatherViewModel by viewModel()

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
        viewModel.getWeatherInfo(isOnline = false)
        observeWeatherError()
        observeWeatherLoading()
        observeWeatherResponse()
    }

    private fun initViews() {

    }

    private fun observeWeatherError() =
        viewModel.responseError.observe(viewLifecycleOwner) { error ->

        }

    private fun observeWeatherLoading() =
        viewModel.responseLoading.observe(viewLifecycleOwner) { loading ->

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
                    weatherCodeText.text = getString(response.dailyWeather.first().weatherCode)
                    weatherIcon.setImageResource(response.dailyWeather.first().weatherIcon)
                }
            }
        }


}