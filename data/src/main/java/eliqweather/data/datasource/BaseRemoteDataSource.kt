package eliqweather.data.datasource

import eliqweather.domain.utils.ErrorHandler
import eliqweather.domain.utils.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
open class BaseRemoteDataSource(private val errorHandler: ErrorHandler) {

    suspend fun <T> safeRequest(apiCall: suspend () -> T): ResultEntity<T> {
        return try {
            ResultEntity.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            errorHandler.getError(throwable).let {
                ResultEntity.Error(it)
            }
        }
    }
}