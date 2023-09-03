package eliqweather.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
abstract class SuspendUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): R =
        withContext(coroutineDispatcher) {
            execute(parameters)
        }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R

}