package eliqweather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
sealed class WeatherInfoModel {

    data class Request(
        val isOnline: Boolean = false,
        val latitude: Double = DEFAULT_LATITUDE,
        val longitude: Double = DEFAULT_LONGITUDE,
        val hourly: String = DEFAULT_HOURLY,
        val daily: String = DEFAULT_DAILY,
        val timezone: String = DEFAULT_TIMEZONE
    ) : WeatherInfoModel()

    data class Response(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val timezone: String? = null,
        val elevation: Double = 0.0,
        val hourlyWeather: List<HourlyWearherModel> = listOf(),
        val dailyWeather: List<DailyWeatherModel> = listOf()
    ) : WeatherInfoModel()

    companion object {
        private const val DEFAULT_LATITUDE = 57.70
        private const val DEFAULT_LONGITUDE = 11.89
        private const val DEFAULT_HOURLY = "temperature_2m,precipitation_probability"
        private const val DEFAULT_DAILY = "temperature_2m_max,temperature_2m_min"
        private const val DEFAULT_TIMEZONE = "auto"
    }
}
