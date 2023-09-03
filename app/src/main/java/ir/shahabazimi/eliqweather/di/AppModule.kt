package ir.shahabazimi.eliqweather.di

import eliqweather.data.config.RetrofitHelper
import eliqweather.data.datasource.LocalWeatherInfoDataSource
import eliqweather.data.datasource.RemoteWeatherInfoDataSource
import eliqweather.data.utils.GeneralErrorHandlerImpl
import eliqweather.domain.repository.LocalWeatherDataSource
import eliqweather.domain.repository.WeatherDataSource
import eliqweather.domain.repository.WeatherRepository
import eliqweather.domain.usecase.GetWeatherInfoUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 03
 **/

val appModule = module {

    factory<eliqweather.data.utils.ErrorHandler> {
        GeneralErrorHandlerImpl(get())
    }

    single {
        RetrofitHelper()
    }

    factory<LocalWeatherDataSource> {
        LocalWeatherInfoDataSource(androidContext())
    }

    factory<WeatherDataSource> {
        RemoteWeatherInfoDataSource(get(), get())
    }

    factory {
        WeatherRepository(get(), get())
    }

    factory {
        GetWeatherInfoUseCase(get(), Dispatchers.IO)
    }

}