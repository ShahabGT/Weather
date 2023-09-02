package eliqweather.data.models

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class HourlyWearherModel(
    private val time: String? = null,
    private val temperature: Double = 0.0,
    private val precipitation: Int = 0,
)
