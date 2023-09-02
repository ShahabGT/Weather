package eliqweather.domain.repository

import eliqweather.domain.models.WeatherInfoModel

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class WeatherRepository(
    private val dataSource: WeatherDataSource,
    private val localDataSource: LocalWeatherDataSource
) {

    suspend fun getWeatherInfo(request: WeatherInfoModel.Request) =
        if (request.isOnline)
            dataSource.getWeatherLocation(request)
        else
            localDataSource.getWeatherLocation()


}