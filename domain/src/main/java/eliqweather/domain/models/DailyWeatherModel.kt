package eliqweather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class DailyWeatherModel(
    val date: String? = null,
    val maxTemperature: Double = 0.0,
    val minTemperature: Double = 0.0,
    val weatherCode: Int,
    val weatherIcon: Int,
)
