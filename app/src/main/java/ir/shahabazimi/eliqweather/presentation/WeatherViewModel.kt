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

    // this variable is used when address is found by the geo locator
    var address: String? = null

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    //sets the location found by fusedLocation services and calls the getWeatherInfo() to ge the weather info
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

    //gets the weather from getWeatherInfoUseCase
    fun getWeatherInfo() =
        viewModelScope.launch {
            _responseLoading.value = true
            getWeatherInfoUseCase.invoke(
                WeatherInfoModel.Request(
                    isOnline = latitude != 0.0 && longitude != 0.0, // if the location is empty we can assume it is in offline mode
                    latitude = latitude.ifZero(DEFAULT_LATITUDE), // if the lat is empty we use default latitude (if its on offline mode it has no use)
                    longitude = longitude.ifZero(DEFAULT_LONGITUDE),
                    hourly = DEFAULT_HOURLY,
                    daily = DEFAULT_DAILY,
                    timezone = DEFAULT_TIMEZONE,
                    forecastDays = DEFAULT_FORECAST_DAYS
                )
            ).updateOnSuccess {
                _response.value = it.apply {
                    if (!address.isNullOrBlank()) // on api response if the address provided by geo locator is not empty this will override the server value
                        it.timezone = address
                }
            }.doOnError {
                _responseError.value = it.message
            }.updateOnComplete {
                _responseLoading.value = false
            }
        }


    //default values for api call
    companion object {
        private const val DEFAULT_LATITUDE = 35.7219
        private const val DEFAULT_LONGITUDE = 51.3347
        private const val DEFAULT_HOURLY =
            "temperature_2m,precipitation_probability" // gets the weather info and precipitation probability hourly
        private const val DEFAULT_DAILY =
            "temperature_2m_max,temperature_2m_min,weathercode" // gets the weather max and min temperature and weather code(like sunny...)
        private const val DEFAULT_TIMEZONE =
            "auto" // we want the server to automatically detects the time zone baseed on user lat lon
        private const val DEFAULT_FORECAST_DAYS = 10 // max daily forecast value
    }


}