package weather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
sealed class WeatherInfoModel {

    data class Request(
        val isOnline: Boolean = false,
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val hourly: String = DEFAULT_HOURLY,
        val daily: String = DEFAULT_DAILY,
        val timezone: String = DEFAULT_TIMEZONE,
        val forecastDays: Int = DEFAULT_FORECAST_DAYS
    ) : WeatherInfoModel()

    data class Response(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        var timezone: String? = null,
        val generationTime: Double = 0.0,
        val elevation: Double = 0.0,
        val hourlyWeather: List<HourlyWeatherModel> = listOf(),
        val dailyWeather: List<DailyWeatherModel> = listOf()
    ) : WeatherInfoModel()

    companion object {
        private const val DEFAULT_HOURLY =
            "temperature_2m,precipitation_probability,weathercode" // gets the weather info and precipitation probability hourly
        private const val DEFAULT_DAILY =
            "temperature_2m_max,temperature_2m_min,weathercode" // gets the weather max and min temperature and weather code(like sunny...)
        private const val DEFAULT_TIMEZONE =
            "auto" // we want the server to automatically detects the time zone based on user lat lon
        private const val DEFAULT_FORECAST_DAYS = 10 // max daily forecast value
    }

}
