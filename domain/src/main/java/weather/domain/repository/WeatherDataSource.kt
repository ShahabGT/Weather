package weather.domain.repository

import weather.domain.models.WeatherInfoModel
import weather.domain.models.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
interface WeatherDataSource {
    suspend fun getWeatherLocation(request: WeatherInfoModel.Request): ResultEntity<WeatherInfoModel.Response>

}