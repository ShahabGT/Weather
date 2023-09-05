package ir.shahabazimi.eliqweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eliqweather.data.utils.doOnError
import eliqweather.data.utils.updateOnComplete
import eliqweather.data.utils.updateOnSuccess
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.usecase.GetWeatherInfoUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase
) : ViewModel() {

    private val _response = MutableLiveData<WeatherInfoModel.Response?>()
    val response: LiveData<WeatherInfoModel.Response?>
        get() = _response

    private val _responseLoading = MutableLiveData<Boolean>()
    val responseLoading: LiveData<Boolean>
        get() = _responseLoading

    private val _responseError = MutableLiveData<String>()
    val responseError: LiveData<String>
        get() = _responseError

    fun getWeatherInfo(
        isOnline: Boolean = false,
        latitude: Double = DEFAULT_LATITUDE,
        longitude: Double = DEFAULT_LONGITUDE,
    ) =
        viewModelScope.launch {
            _responseLoading.value = true
            getWeatherInfoUseCase.invoke(
                WeatherInfoModel.Request(
                    isOnline = isOnline,
                    latitude = latitude,
                    longitude = longitude,
                    hourly = DEFAULT_HOURLY,
                    daily = DEFAULT_DAILY,
                    timezone = DEFAULT_TIMEZONE,
                    forecastDays = DEFAULT_FORECAST_DAYS
                )
            ).updateOnSuccess {
                _response.value = it
            }.doOnError {
                _responseError.value = it.message
            }.updateOnComplete {
                _responseLoading.value = false
            }
        }


    companion object {
        private const val DEFAULT_LATITUDE = 35.7219
        private const val DEFAULT_LONGITUDE = 51.3347
        private const val DEFAULT_HOURLY = "temperature_2m,precipitation_probability"
        private const val DEFAULT_DAILY = "temperature_2m_max,temperature_2m_min,weathercode"
        private const val DEFAULT_TIMEZONE = "auto"
        private const val DEFAULT_FORECAST_DAYS = 16
    }


}