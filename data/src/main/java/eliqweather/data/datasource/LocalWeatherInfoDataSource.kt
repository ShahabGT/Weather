package eliqweather.data.datasource

import android.content.Context
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
//this datasource implements the LocalWeatherDataSource which can be found in Domain module
class LocalWeatherInfoDataSource constructor(private val context: Context) : LocalWeatherDataSource,
    BaseLocalDataSource() {
    override suspend fun getWeatherLocation(): ResultEntity<WeatherInfoModel.Response> =
        safeTransaction {
            //getting the json from raw folder and using gson to convert it to our data class
            val sampleWeather = context.resources.openRawResource(R.raw.sample_weather).bufferedReader()
                .use { it.readText() }

            val data = Gson().fromJson(sampleWeather, WeatherResponse::class.java).toDomain()

            return@safeTransaction ResultEntity.Success(data)
        }

}