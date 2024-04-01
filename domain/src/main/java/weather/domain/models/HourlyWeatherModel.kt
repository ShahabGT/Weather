package weather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class HourlyWeatherModel(
    val time: String? = null,
    val temperature: String? = null,
    val precipitation: Int = 0,
    val weatherCode: Int,
    val weatherIcon: Int,
)
