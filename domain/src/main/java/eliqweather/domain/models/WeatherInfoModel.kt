package eliqweather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
sealed class WeatherInfoModel {

    data class Request(
        val isOnline: Boolean = false,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val hourly: String = "",
        val daily: String = "",
        val timezone: String = ""
    ) : WeatherInfoModel()

    data class Response(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val timezone: String? = null,
        val elevation: Double = 0.0,
        val hourlyWeather: List<HourlyWearherModel> = listOf(),
        val dailyWeather: List<DailyWeatherModel> = listOf()
    ) : WeatherInfoModel()

}
