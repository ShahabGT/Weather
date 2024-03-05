package weather.data.mapper

import weather.data.R

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
//converts the weather code to a pair of title and animation
fun getWeatherCondition(code: Int) =
    when (code) {
        0 -> Pair(R.string.clear, R.raw.sunny)
        1 -> Pair(R.string.mainly_clear, R.raw.partly_cloudy)
        2 -> Pair(R.string.partly_cloudy, R.raw.partly_cloudy)
        3 -> Pair(R.string.overcast, R.raw.overcast)
        45 -> Pair(R.string.fog, R.raw.fog)
        48 -> Pair(R.string.rime_fog, R.raw.overcast)
        51 -> Pair(R.string.light_drizzle, R.raw.drizzle)
        53 -> Pair(R.string.drizzle, R.raw.drizzle)
        55 -> Pair(R.string.dense_drizzle, R.raw.drizzle)
        56 -> Pair(R.string.light_freezing_drizzle, R.raw.drizzle)
        57 -> Pair(R.string.dense_freezing_drizzle, R.raw.drizzle)
        61 -> Pair(R.string.slight_rain, R.raw.slight_rain)
        63 -> Pair(R.string.rain, R.raw.rain)
        65 -> Pair(R.string.heavy_rain, R.raw.rain)
        66 -> Pair(R.string.light_freezing_rain, R.raw.rain)
        67 -> Pair(R.string.heavy_freezing_rain, R.raw.rain)
        71 -> Pair(R.string.light_snow, R.raw.snow)
        73 -> Pair(R.string.snow, R.raw.snow)
        75 -> Pair(R.string.heavy_snow, R.raw.snow)
        77 -> Pair(R.string.snow_grains, R.raw.snow)
        80 -> Pair(R.string.slight_rain_showers, R.raw.slight_rain)
        81 -> Pair(R.string.rain_showers, R.raw.rain)
        82 -> Pair(R.string.heavy_rain_showers, R.raw.rain)
        85 -> Pair(R.string.light_snow_showers, R.raw.snow)
        86 -> Pair(R.string.heavy_snow_showers, R.raw.snow)
        95 -> Pair(R.string.thunderstorm, R.raw.thunderstorm)
        96 -> Pair(R.string.slight_hail_thunderstorm, R.raw.thunder)
        99 -> Pair(R.string.heavy_hail_thunderstorm, R.raw.thunderstorm)
        else -> Pair(R.string.unknown, R.raw.sunny)
    }