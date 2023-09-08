package ir.shahabazimi.eliqweather

import android.app.Application
import ir.shahabazimi.eliqweather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class EliqApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EliqApplication)
            modules(appModule)
        }
    }

}