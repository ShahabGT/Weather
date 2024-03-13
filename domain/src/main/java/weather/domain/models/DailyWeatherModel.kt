package weather.domain.models

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
) {
    override fun equals(other: Any?): Boolean {
        val data = other as DailyWeatherModel
        return date == data.date &&
                maxTemperature == data.maxTemperature &&
                minTemperature == data.minTemperature &&
                weatherCode == data.weatherIcon
    }

    override fun hashCode(): Int {
        var result = date?.hashCode() ?: 0
        result = 31 * result + maxTemperature.hashCode()
        result = 31 * result + minTemperature.hashCode()
        result = 31 * result + weatherCode
        result = 31 * result + weatherIcon
        return result
    }
}
