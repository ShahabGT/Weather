package eliqweather.data.utils

import eliqweather.domain.models.ErrorEntity
import eliqweather.domain.models.ResultEntity

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