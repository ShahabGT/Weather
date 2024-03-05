package ir.shahabazimi.weather.di

import weather.data.config.RetrofitHelper
import weather.data.datasource.LocalWeatherInfoDataSource
import weather.data.datasource.RemoteWeatherInfoDataSource
import weather.data.utils.GeneralErrorHandlerImpl
import weather.domain.repository.LocalWeatherDataSource
import weather.domain.repository.WeatherDataSource
import weather.domain.repository.WeatherRepository
import weather.domain.usecase.GetWeatherInfoUseCase
import ir.shahabazimi.weather.presentation.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import weather.data.utils.ErrorHandler

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 03
 **/
//apps koin dependency injection
val appModule = module {

    factory<ErrorHandler> {
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

    viewModel {
        WeatherViewModel(get())
    }

}