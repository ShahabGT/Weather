package eliqweather.domain.repository

import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.utils.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
interface LocalWeatherDataSource {
    suspend fun getWeatherLocation(): ResultEntity<WeatherInfoModel.Response>

}