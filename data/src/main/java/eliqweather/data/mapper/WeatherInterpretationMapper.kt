package eliqweather.data.mapper

import eliqweather.data.R

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 04
 **/
fun getWeatherCondition(code: Int) =
    when (code) {
        0 -> Pair(R.string.clear, R.drawable.sunny)
        1 -> Pair(R.string.mainly_clear, R.drawable.cloudy)
        2 -> Pair(R.string.partly_cloudy, R.drawable.cloudy)
        3 -> Pair(R.string.overcast, R.drawable.cloudy)
        45 -> Pair(R.string.fog, R.drawable.cloudy)
        48 -> Pair(R.string.rime_fog, R.drawable.cloudy)
        51 -> Pair(R.string.light_drizzle, R.drawable.drizzle)
        53 -> Pair(R.string.drizzle, R.drawable.drizzle)
        55 -> Pair(R.string.dense_drizzle, R.drawable.drizzle)
        56 -> Pair(R.string.light_freezing_drizzle, R.drawable.drizzle)
        57 -> Pair(R.string.dense_freezing_drizzle, R.drawable.drizzle)
        61 -> Pair(R.string.slight_rain, R.drawable.rainy)
        63 -> Pair(R.string.rain, R.drawable.rainy)
        65 -> Pair(R.string.heavy_rain, R.drawable.rainy)
        66 -> Pair(R.string.light_freezing_rain, R.drawable.rainy)
        67 -> Pair(R.string.heavy_freezing_rain, R.drawable.rainy)
        71 -> Pair(R.string.light_snow, R.drawable.snowy)
        73 -> Pair(R.string.snow, R.drawable.snowy)
        75 -> Pair(R.string.heavy_snow, R.drawable.snowy)
        77 -> Pair(R.string.snow_grains, R.drawable.snowy)
        80 -> Pair(R.string.slight_rain_showers, R.drawable.rainy)
        81 -> Pair(R.string.rain_showers, R.drawable.rainy)
        82 -> Pair(R.string.heavy_rain_showers, R.drawable.rainy)
        85 -> Pair(R.string.light_snow_showers, R.drawable.snowy)
        86 -> Pair(R.string.heavy_snow_showers, R.drawable.snowy)
        95 -> Pair(R.string.thunderstorm, R.drawable.thunderstorm)
        96 -> Pair(R.string.slight_hail_thunderstorm, R.drawable.thunderstorm)
        99 -> Pair(R.string.heavy_hail_thunderstorm, R.drawable.thunderstorm)
        else -> Pair(R.string.unknown, R.drawable.sunny)
    }