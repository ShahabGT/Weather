package weather.data.mapper

import weather.data.utils.orZero
import weather.domain.models.Daily
import weather.domain.models.DailyWeatherModel
import weather.domain.models.Hourly
import weather.domain.models.HourlyWeatherModel
import weather.domain.models.WeatherInfoModel
import weather.domain.models.WeatherResponse

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/

//converts the api call response to WeatherInfo Model
fun WeatherResponse.toDomain() = WeatherInfoModel.Response(
    latitude = latitude,
    longitude = longitude,
    timezone = timezone,
    generationTime = generationTime,
    elevation = elevation,
    hourlyWeather = hourly.toDomain(),
    dailyWeather = daily.toDomain()
)

fun Hourly.toDomain(): List<HourlyWeatherModel> {
    val response = mutableListOf<HourlyWeatherModel>()
    time.forEachIndexed { index, data ->
        response.add(
            HourlyWeatherModel(
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
                minTemperature = temperature_2m_min[index].orZero(),
                weatherCode = getWeatherCondition(weathercode[index].orZero()).first, // used getWeatherCondition for getting the corresponding weather code title
                weatherIcon = getWeatherCondition(weathercode[index].orZero()).second// used getWeatherCondition for getting the corresponding weather code animation
            )
        )
    }

    return response
}