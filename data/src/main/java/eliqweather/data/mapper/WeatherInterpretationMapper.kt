package eliqweather.data.mapper

import eliqweather.data.R

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
fun getWeatherCondition(code: Int) =
    when (code) {
        0 -> R.string.clear
        1 -> R.string.mainly_clear
        2 -> R.string.partly_cloudy
        3 -> R.string.overcast
        45 -> R.string.fog
        48 -> R.string.rime_fog
        51 -> R.string.light_drizzle
        53 -> R.string.drizzle
        55 -> R.string.dense_drizzle
        56 -> R.string.light_freezing_drizzle
        57 -> R.string.dense_freezing_drizzle
        61 -> R.string.slight_rain
        63 -> R.string.rain
        65 -> R.string.heavy_rain
        66 -> R.string.light_freezing_rain
        67 -> R.string.heavy_freezing_rain
        71 -> R.string.light_snow
        73 -> R.string.snow
        75 -> R.string.heavy_snow
        77 -> R.string.snow_grains
        80 -> R.string.slight_rain_showers
        81 -> R.string.rain_showers
        82 -> R.string.heavy_rain_showers
        85 -> R.string.light_snow_showers
        86 -> R.string.heavy_snow_showers
        95 -> R.string.thunderstorm
        96 -> R.string.slight_hail_thunderstorm
        99 -> R.string.heavy_hail_thunderstorm
        else -> R.string.unknown
    }