package eliqweather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class DailyWeatherModel(
    private val date: String? = null,
    private val maxTemperature: Double = 0.0,
    private val minTemperature: Double = 0.0,
    private val weatherCode: Int
)
