package weather.domain.repository

import weather.domain.models.WeatherInfoModel
import weather.domain.models.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
interface LocalWeatherDataSource {
    suspend fun getWeatherLocation(): ResultEntity<WeatherInfoModel.Response>

}