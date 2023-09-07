package eliqweather.data.utils

import android.view.View
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
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this.orEmpty())
    val outputFormatter = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
    return date?.let { outputFormatter.format(it) } ?: ""
}

fun String?.convertToDayDate(): String {
    return if (dateIsToday(this.orEmpty())) {
        "Today"
    } else {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this.orEmpty())
        val outputFormatter = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
        date?.let { outputFormatter.format(it) } ?: ""
    }
}

fun getHourOfDay() =
    Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

private fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    return "$year-$month-$dayOfMonth"
}

fun dateIsToday(date: String): Boolean {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = getTodayDate()
    return try {
        val date1 = dateFormat.parse(today)
        val date2 = dateFormat.parse(date)
        !date1?.before(date2)!! && !date1.after(date2)
    } catch (e: Exception) {
        false
    }
}

fun View.visibilityState(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Double?.ifZero(value: Double) = if (this == null || this == 0.0) value else this
