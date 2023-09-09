package eliqweather.data.mapper

import eliqweather.data.utils.orZero
import eliqweather.domain.models.Daily
import eliqweather.domain.models.DailyWeatherModel
import eliqweather.domain.models.Hourly
import eliqweather.domain.models.HourlyWearherModel
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.models.WeatherResponse

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
                minTemperature = temperature_2m_min[index].orZero(),
                weatherCode = getWeatherCondition(weathercode[index].orZero()).first, // used getWeatherCondition for getting the corresponding weather code title
                weatherIcon = getWeatherCondition(weathercode[index].orZero()).second// used getWeatherCondition for getting the corresponding weather code animation
            )
        )
    }

    return response
}