package weather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class HourlyWeatherModel(
    val time: String? = null,
    val temperature: Double = 0.0,
    val precipitation: Int = 0,
)
