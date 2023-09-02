package eliqweather.domain.models

import com.google.gson.annotations.SerializedName

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
sealed class WeatherInfoModel {

    data class request(
        val isOnline: Boolean = false,
        val latitude: Double = DEFAULT_LATITUDE,
        val longitude: Double = DEFAULT_LONGITUDE,
        val hourly: String = DEFAULT_HOURLY,
        val daily: String = DEFAULT_DAILY,
        val timezone: String = DEFAULT_TIMEZONE
    ) : WeatherInfoModel()

    data class response(
        val daily: Daily,
        @SerializedName("daily_units")
        val dailyUnits: DailyUnits,
        val elevation: Double,
        @SerializedName("generationtime_ms")
        val generationTime: Double,
        val hourly: Hourly,
        @SerializedName("hourly_units")
        val hourlyUnits: HourlyUnits,
        val latitude: Double,
        val longitude: Double,
        val timezone: String,
        @SerializedName("timezoneAbbreviation")
        val timezone_abbreviation: String,
        @SerializedName("utc_offset_seconds")
        val utcOffsetInSeconds: Int
    ) : WeatherInfoModel()

    companion object {
        private const val DEFAULT_LATITUDE = 57.70
        private const val DEFAULT_LONGITUDE = 11.89
        private const val DEFAULT_HOURLY = "temperature_2m,precipitation_probability"
        private const val DEFAULT_DAILY = "temperature_2m_max,temperature_2m_min"
        private const val DEFAULT_TIMEZONE = "auto"
    }
}
