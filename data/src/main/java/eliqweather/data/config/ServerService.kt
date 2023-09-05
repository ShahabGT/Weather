package eliqweather.data.config

import androidx.annotation.WorkerThread
import eliqweather.domain.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
interface ServerService {
    @WorkerThread
    @GET("forecast")
    suspend fun getWeatherInfo(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("daily") daily: String,
        @Query("timezone") timezone: String,
        @Query("forecast_days") forecastDays : Int
    ): WeatherResponse

}