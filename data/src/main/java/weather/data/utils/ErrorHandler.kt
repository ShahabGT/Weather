package weather.data.utils

import weather.domain.models.ErrorEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}
