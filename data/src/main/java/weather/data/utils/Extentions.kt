package weather.data.utils

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import weather.domain.models.ErrorEntity
import weather.domain.models.ResultEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.math.ceil

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/

fun Int?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0.0

fun String?.ifNullOrEmpty(value: String) = if (this.isNullOrBlank()) value else this


//upon getting the result if is type of ResultEntity.Success the call back will be called
inline fun <reified T> ResultEntity<T>.updateOnSuccess(callback: (T) -> Unit): ResultEntity<T> {
    if (this is ResultEntity.Success) {
        callback.invoke(data)
    }
    return this
}

//upon getting the result if is type of ResultEntity.Error the call back will be called
inline fun <reified T> ResultEntity<T>.doOnError(onError: (ErrorEntity) -> Unit): ResultEntity<T> {
    if (this is ResultEntity.Error) {
        onError.invoke(this.error)
    }
    return this
}

//after getting the result whatever its Success or Error this callback will be called
inline fun <reified T> ResultEntity<T>.updateOnComplete(callback: () -> Unit): ResultEntity<T> {
    if (this is ResultEntity.Success || this is ResultEntity.Error) {
        callback()
    }
    return this
}

//converts the 2023-09-09 date to Sunday, Sep 09
fun String?.convertToReadableDate(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this.orEmpty())
    val outputFormatter = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
    return date?.let { outputFormatter.format(it) } ?: ""
}

//converts the 2023-09-09 date to Sunday, Sep 09 if the date is today the Today title wil be returned
fun String?.convertToDayDate(): String {
    return if (dateIsToday(this.orEmpty())) {
        "Today"
    } else {
        this.convertToReadableDate()
    }
}

//getting the current hour of day
fun getHourOfDay() =
    Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

//getting today's date
private fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    return "$year-$month-$dayOfMonth"
}

//compare current date and second date to check whether the second date is today
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

fun Double?.roundToNearestInt(): String {
    val roundedValue = if (this.orZero() % 1 >= 0.5) {
        Math.round(this.orZero()).toInt()
    } else {
        ceil(this.orZero()).toInt()
    }
    return roundedValue.toString()
}

//an extension function for live data to wait for value of the live data
//used for unit test only
fun <T> LiveData<T>.getOrAwaitForResult(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            if (value is ResultEntity<*>) {
                data = value
                latch.countDown()
                this@getOrAwaitForResult.removeObserver(this)
            }
        }
    }
    this.observeForever(observer)

    afterObserve.invoke()

    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}