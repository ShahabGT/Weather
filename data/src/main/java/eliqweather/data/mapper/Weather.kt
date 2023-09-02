package eliqweather.data.mapper

import eliqweather.data.models.Daily
import eliqweather.data.models.DailyWeatherModel
import eliqweather.data.models.Hourly
import eliqweather.data.models.HourlyWearherModel
import eliqweather.data.models.WeatherModel
import eliqweather.data.models.WeatherResponse
import eliqweather.data.utils.orZero

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/

fun WeatherResponse.toDomain() = WeatherModel(
    latitude = latitude,
    longitude = longitude,
    timezone = timezone,
    elevation = elevation,
    hourlyWeather = hourly.toDomain(),
    dailyWeather = daily.toDomain()
)

fun Hourly.toDomain(): List<HourlyWearherModel> {
    val response = mutableListOf<HourlyWearherModel>()
    time.forEachIndexed { index, data ->
        response.add(
            HourlyWearherModel(
                time = data,
                precipitation = precipitation_probability[index].orZero(),
                temperature = temperature_2m[index].orZero()
            )
        )
    }
    return response
}

fun Daily.toDomain(): List<DailyWeatherModel> {
    val response = mutableListOf<DailyWeatherModel>()
    time.forEachIndexed { index, data ->
        response.add(
            DailyWeatherModel(
                date = data,
                maxTemperature = temperature_2m_max[index].orZero(),
                minTemperature = temperature_2m_min[index].orZero()
            )
        )
    }

    return response
}