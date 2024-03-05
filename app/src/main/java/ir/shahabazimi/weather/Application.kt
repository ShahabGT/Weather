package ir.shahabazimi.weather

import android.app.Application
import ir.shahabazimi.weather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        //initializing koin for app
        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }
    }

}