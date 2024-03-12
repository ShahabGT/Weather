package weather.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import weather.domain.models.WeatherInfoModel
import weather.domain.repository.WeatherRepository

/**
 * @Author: Shahab Azimi
 * @Date: 2024 - 03 - 13
 **/
class SaveWeatherInfoUseCase (
    private val repository: WeatherRepository,
    defaultDispatcher: CoroutineDispatcher
) : SuspendUseCase<WeatherInfoModel.Response, Unit>(
    defaultDispatcher
) {

    override suspend fun execute(parameters: WeatherInfoModel.Response) =
        repository.saveWeatherInfo(parameters)

}