package ir.shahabazimi.weather

import ir.shahabazimi.weather.di.appModule
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 06
 **/
class CheckModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAppModule() {
        appModule.verify(extraTypes = listOf(CoroutineDispatcher::class))
    }
}