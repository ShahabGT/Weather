package weather.data.datasource

import weather.domain.models.ErrorEntity
import weather.domain.models.ResultEntity
import java.io.IOException

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
//base for calling offline api call
open class BaseLocalDataSource {

    // this is generic coroutine function which calls the provided callback which is type of ResultEntity
    suspend fun <T> safeTransaction(callback: suspend () -> ResultEntity<T>): ResultEntity<T> {
        return try {
            return callback.invoke()
        } catch (e: IOException) {
            // if something went wrong we throw a general error which will be handled in view
            ResultEntity.Error(ErrorEntity.Generic(e.message.orEmpty()))
        }
    }

}