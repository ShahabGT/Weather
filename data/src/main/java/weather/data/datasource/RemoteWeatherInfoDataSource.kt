package weather.data.datasource

import weather.data.config.RetrofitHelper
import weather.data.mapper.toDomain
import weather.domain.models.WeatherInfoModel
import weather.domain.repository.WeatherDataSource
import weather.data.utils.ErrorHandler
import weather.domain.models.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
//this datasource implements the WeatherDataSource which can be found in Domain module
class RemoteWeatherInfoDataSource(
    private val retrofitHelper: RetrofitHelper,
    errorHandler: ErrorHandler
) : WeatherDataSource, BaseRemoteDataSource(errorHandler) {
    override suspend fun getWeatherLocation(request: WeatherInfoModel.Request): ResultEntity<WeatherInfoModel.Response> =
        // calls the api with the provided request model and converts it to our WeatherInfoModel with .domain extension function
        safeRequest {
            with(request) {
                retrofitHelper.serverService.getWeatherInfo(
                    latitude = latitude,
                    longitude = longitude,
                    daily = daily,
                    hourly = hourly,
                    timezone = timezone,
                    forecastDays = forecastDays
                ).toDomain()
            }
        }
}