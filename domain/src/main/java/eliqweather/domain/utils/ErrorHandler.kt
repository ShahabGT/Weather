package eliqweather.domain.utils

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}
