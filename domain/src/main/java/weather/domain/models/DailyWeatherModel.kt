package weather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class DailyWeatherModel(
    val date: String? = null,
    val maxTemperature: String? = null,
    val minTemperature: String? = null,
    val weatherCode: Int,
    val weatherIcon: Int,
)
