package weather.domain.models

import com.google.gson.annotations.SerializedName
import weather.domain.models.Daily
import weather.domain.models.Hourly

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
    @SerializedName("timezoneAbbreviation")
    val timezone_abbreviation: String,
    @SerializedName("utc_offset_seconds")
    val utcOffsetInSeconds: Int
)