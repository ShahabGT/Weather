package eliqweather.data.datasource

import eliqweather.domain.utils.ErrorEntity
import eliqweather.domain.utils.ResultEntity
import java.io.IOException

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
open class BaseLocalDataSource {

    suspend fun <T> safeTransaction(callback: suspend () -> ResultEntity<T>): ResultEntity<T> {
        return try {
            return callback.invoke()
        } catch (e: IOException) {
            ResultEntity.Error(ErrorEntity.Generic(e.message.orEmpty()))
        }
    }

}