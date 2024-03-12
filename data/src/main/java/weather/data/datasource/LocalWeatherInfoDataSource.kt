package weather.data.datasource

import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import weather.domain.models.ResultEntity
import weather.domain.models.WeatherInfoModel
import weather.domain.models.WeatherResponseKey
import weather.domain.repository.DataStoreProvider
import weather.domain.repository.LocalWeatherDataSource

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
//this datasource implements the LocalWeatherDataSource which can be found in Domain module
class LocalWeatherInfoDataSource(private val dataStoreProvider: DataStoreProvider) :
    LocalWeatherDataSource, BaseLocalDataSource() {
    override suspend fun getWeatherLocation(): ResultEntity<WeatherInfoModel.Response> =
        safeTransaction {
            val rawData = runBlocking {
                dataStoreProvider.weatherDataStore.data.first()
            }

            val data = Gson().fromJson(
                rawData[WeatherResponseKey.DATA],
                WeatherInfoModel.Response::class.java
            )

            return@safeTransaction ResultEntity.Success(data)

        }

    override suspend fun saveWeatherLocation(data: WeatherInfoModel.Response) {
        dataStoreProvider.weatherDataStore.edit {
            it[WeatherResponseKey.DATA] = Gson().toJson(data)
        }
    }

}