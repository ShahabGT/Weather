package eliqweather.data.utils

import android.content.Context
import eliqweather.data.R
import eliqweather.domain.models.ErrorEntity
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.UnknownHostException

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class GeneralErrorHandlerImpl constructor(
    private val context: Context
) : ErrorHandler {
    override fun getError(throwable: Throwable): ErrorEntity {

        return when (throwable) {

            is UnknownHostException -> ErrorEntity.UnknownHost.apply {
                message = context.getString(R.string.no_intent)
            }

            is MalformedURLException -> ErrorEntity.Network.apply {
                message = context.getString(R.string.server_error)

            }

            is IOException -> ErrorEntity.Network.apply {
                message = context.getString(R.string.server_error)

            }

            is HttpException -> convertHttpErrorBody(
                throwable = throwable
            )

            else -> ErrorEntity.Generic(
                data = throwable.message ?: throwable.toString()
            )
        }
    }

    private fun convertHttpErrorBody(throwable: HttpException): ErrorEntity {
        val code = throwable.response()?.code() ?: 500
        return ErrorEntity.ApiError(
            data = getErrorMessage(throwable),
            errorCode = code
        )
    }

    private fun getErrorMessage(throwable: HttpException) =
        getErrorResponseHttpException(throwable)?.let(::getErrorMessageFromByteArr).ifNullOrEmpty(
            context.getString(R.string.server_error)
        )


    private fun getErrorResponseHttpException(throwable: HttpException): ByteArray? = try {
        val bufferedSource = throwable.response()?.errorBody()?.source()
        bufferedSource?.run {
            val result = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var length: Int
            while (bufferedSource.read(buffer).also { length = it } != -1) {
                result.write(buffer, 0, length)
            }
            result.toByteArray()
        }
    } catch (e: IllegalStateException) {
        null
    }

    private fun getErrorMessageFromByteArr(data: ByteArray): String {
        return try {
            data.toString(Charsets.UTF_8)
        } catch (e: Exception) {
            context.getString(R.string.server_error)
        }.ifNullOrEmpty(
            context.getString(R.string.unknown_error)
        )
    }


}