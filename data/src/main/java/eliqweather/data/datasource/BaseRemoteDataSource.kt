package eliqweather.data.datasource

import eliqweather.data.utils.ErrorHandler
import eliqweather.domain.models.ResultEntity

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
//base for online api calls
open class BaseRemoteDataSource(private val errorHandler: ErrorHandler) {

    // this is generic coroutine function which calls the provided callback which is type of ResultEntity
    suspend fun <T> safeRequest(apiCall: suspend () -> T): ResultEntity<T> {
        return try {
            ResultEntity.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            errorHandler.getError(throwable).let {
                // if something went wrong we throw an error(implemented in ErrorHandler) which will be handled in view
                ResultEntity.Error(it)
            }
        }
    }
}