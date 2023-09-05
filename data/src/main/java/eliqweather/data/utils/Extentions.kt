package eliqweather.data.utils

import eliqweather.domain.models.ErrorEntity
import eliqweather.domain.models.ResultEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/

fun Int?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0.0

fun String?.ifNullOrEmpty(value: String) = if (this.isNullOrBlank()) value else this


inline fun <reified T> ResultEntity<T>.updateOnSuccess(callback: (T) -> Unit): ResultEntity<T> {
    if (this is ResultEntity.Success) {
        callback.invoke(data)
    }
    return this
}

inline fun <reified T> ResultEntity<T>.doOnError(onError: (ErrorEntity) -> Unit): ResultEntity<T> {
    if (this is ResultEntity.Error) {
        onError.invoke(this.error)
    }
    return this
}

inline fun <reified T> ResultEntity<T>.updateOnComplete(callback: () -> Unit): ResultEntity<T> {
    if (this is ResultEntity.Success || this is ResultEntity.Error) {
        callback()
    }
    return this
}

fun String?.convertToReadableDate(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
    val outputFormatter = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
    return date?.let { outputFormatter.format(it) } ?: ""
}

fun getHourOfDay() =
    Calendar.getInstance().get(Calendar.HOUR_OF_DAY)