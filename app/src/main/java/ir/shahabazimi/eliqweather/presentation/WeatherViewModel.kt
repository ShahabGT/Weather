package ir.shahabazimi.eliqweather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eliqweather.data.utils.doOnError
import eliqweather.data.utils.ifZero
import eliqweather.data.utils.updateOnComplete
import eliqweather.data.utils.updateOnSuccess
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.usecase.GetWeatherInfoUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase
) : ViewModel() {

    var address: String = ""

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    fun setLocation(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        getWeatherInfo()
    }

    private val _response = MutableLiveData<WeatherInfoModel.Response?>()
    val response: LiveData<WeatherInfoModel.Response?>
        get() = _response

    private val _responseLoading = MutableLiveData<Boolean>()
    val responseLoading: LiveData<Boolean>
        get() = _responseLoading

    private val _responseError = MutableLiveData<String>()
    val responseError: LiveData<String>
        get() = _responseError

    fun getWeatherInfo() =
        viewModelScope.launch {
            _responseLoading.value = true
            getWeatherInfoUseCase.invoke(
                WeatherInfoModel.Request(
                    isOnline = latitude != 0.0 && longitude != 0.0,
                    latitude = latitude.ifZero(DEFAULT_LATITUDE),
                    longitude = longitude.ifZero(DEFAULT_LONGITUDE),
                    hourly = DEFAULT_HOURLY,
                    daily = DEFAULT_DAILY,
                    timezone = DEFAULT_TIMEZONE,
                    forecastDays = DEFAULT_FORECAST_DAYS
                )
            ).updateOnSuccess {
                _response.value = it.apply {
                    if (address.isNotBlank())
                        it.timezone = address
                }
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
        private const val DEFAULT_FORECAST_DAYS = 10
    }


}