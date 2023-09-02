package eliqweather.domain.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class Hourly(
    val precipitation_probability: List<Int>,
    val temperature_2m: List<Double>,
    val time: List<String>
)