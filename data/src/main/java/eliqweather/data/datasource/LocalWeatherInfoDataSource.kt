package eliqweather.data.datasource

import android.content.res.Resources
import com.google.gson.Gson
import eliqweather.data.R
import eliqweather.data.mapper.toDomain
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.models.WeatherResponse
import eliqweather.domain.repository.LocalWeatherDataSource
import eliqweather.domain.models.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class LocalWeatherInfoDataSource constructor(private val resources: Resources) : LocalWeatherDataSource,
    BaseLocalDataSource() {
    override suspend fun getWeatherLocation(): ResultEntity<WeatherInfoModel.Response> =
        safeTransaction {
            val sampleWeather = resources.openRawResource(R.raw.sample_weather).bufferedReader()
                .use { it.readText() }

            val data = Gson().fromJson(sampleWeather, WeatherResponse::class.java).toDomain()

            return@safeTransaction ResultEntity.Success(data)
        }

}