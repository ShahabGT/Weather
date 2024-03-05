package weather.data.datasource

import android.content.Context
import com.google.gson.Gson
import weather.data.R
import weather.data.mapper.toDomain
import weather.domain.models.WeatherInfoModel
import weather.domain.models.WeatherResponse
import weather.domain.repository.LocalWeatherDataSource
import weather.domain.models.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
//this datasource implements the LocalWeatherDataSource which can be found in Domain module
class LocalWeatherInfoDataSource(private val context: Context) : LocalWeatherDataSource,
    BaseLocalDataSource() {
    override suspend fun getWeatherLocation(): ResultEntity<WeatherInfoModel.Response> =
        safeTransaction {
            //getting the json from raw folder and using gson to convert it to our data class
            val sampleWeather =
                context.resources.openRawResource(R.raw.sample_weather).bufferedReader()
                    .use { it.readText() }

            val data = Gson().fromJson(sampleWeather, WeatherResponse::class.java).toDomain()

            return@safeTransaction ResultEntity.Success(data)
        }

}