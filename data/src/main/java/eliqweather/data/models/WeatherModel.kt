package eliqweather.data.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class WeatherModel(
    private val latitude: Double = 0.0,
    private val longitude: Double = 0.0,
    private val timezone: String? = null,
    private val elevation: Double = 0.0,
    private val hourlyWeather: List<HourlyWearherModel> = listOf(),
    private val dailyWeather: List<DailyWeatherModel> = listOf()
)
