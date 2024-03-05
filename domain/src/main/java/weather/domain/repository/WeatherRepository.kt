package weather.domain.repository

import weather.domain.models.WeatherInfoModel


/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class WeatherRepository(
    private val dataSource: WeatherDataSource,
    private val localDataSource: LocalWeatherDataSource
) {

    // if the request.isOnline is false we use WeatherData source for api call otherwise LocalWeatherDataSource is used for getting the data from the sample json file
    suspend fun getWeatherInfo(request: WeatherInfoModel.Request) =
        if (request.isOnline)
            dataSource.getWeatherLocation(request)
        else
            localDataSource.getWeatherLocation()


}