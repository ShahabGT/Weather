package ir.shahabazimi.weather.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import weather.data.utils.doOnError
import weather.data.utils.updateOnSuccess
import weather.domain.models.WeatherInfoModel
import weather.domain.usecase.GetWeatherInfoUseCase
import weather.domain.usecase.SaveWeatherInfoUseCase

class WeatherViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val saveWeatherInfoUseCase: SaveWeatherInfoUseCase,
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

    val _responseLoading = MutableLiveData<Boolean>()
    val responseLoading: LiveData<Boolean>
        get() = _responseLoading

    private val _responseError = MutableLiveData<String>()
    val responseError: LiveData<String>
        get() = _responseError

    //gets the weather from getWeatherInfoUseCase
    fun getWeatherInfo(isOnline:Boolean=true) =
        viewModelScope.launch {
            _responseLoading.value = true
            getWeatherInfoUseCase.invoke(
                WeatherInfoModel.Request(
                    isOnline = isOnline,
                    latitude = latitude,
                    longitude = longitude
                    )
            ).updateOnSuccess {
                _responseLoading.value = false
                _response.value = it.apply {
                    if (!address.isNullOrBlank()) // on api response if the address provided by geo locator is not empty this will override the server value
                        it.timezone = address
                }
                if (isOnline && _response.value != null)
                    saveWeatherInfoUseCase.invoke(_response.value!!)

            }.doOnError {
                _responseError.value = it.message
                _responseLoading.value = false
            }
        }


}