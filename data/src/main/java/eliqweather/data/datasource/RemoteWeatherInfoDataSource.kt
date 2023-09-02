package eliqweather.data.datasource

import eliqweather.data.config.RetrofitHelper
import eliqweather.domain.mapper.toDomain
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.repository.WeatherDataSource
import eliqweather.domain.utils.ErrorHandler
import eliqweather.domain.utils.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class RemoteWeatherInfoDataSource(
    private val retrofitHelper: RetrofitHelper,
    errorHandler: ErrorHandler
) : WeatherDataSource, BaseRemoteDataSource(errorHandler) {
    override suspend fun getWeatherLocation(request: WeatherInfoModel.Request): ResultEntity<WeatherInfoModel.Response> =
        safeRequest {
            with(request) {
                retrofitHelper.serverService.getWeatherInfo(
                    latitude = latitude.toString(),
                    longitude = longitude.toString(),
                    daily = daily,
                    hourly = hourly,
                    timezone = timezone
                ).toDomain()
            }
        }
}