package eliqweather.domain.utils

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
sealed class ErrorEntity(var message: String, val code: Int) {

    data class ApiError(val data: String, val errorCode: Int) : ErrorEntity(data, errorCode)

    data class Generic(var data: String) : ErrorEntity(data, -3)

    object Network : ErrorEntity("No Network Access", -1)

    object UnknownHost : ErrorEntity("Unknown host", -2)
}
