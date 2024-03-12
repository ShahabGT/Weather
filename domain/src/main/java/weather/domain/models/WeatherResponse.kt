package weather.domain.models

import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.annotations.SerializedName

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
data class WeatherResponse(
    val daily: Daily,
    val elevation: Double,
    @SerializedName("generationtime_ms")
    val generationTime: Double,
    val hourly: Hourly,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    @SerializedName("utc_offset_seconds")
    val utcOffsetInSeconds: Int
)

object WeatherResponseKey {
    val DATA = stringPreferencesKey("weather_data")
}