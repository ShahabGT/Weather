package eliqweather.domain.utils

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/

fun Int?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0.0