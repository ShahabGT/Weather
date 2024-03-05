package weather.domain.usecase

import weather.domain.models.WeatherInfoModel
import weather.domain.repository.WeatherRepository
import weather.domain.models.ResultEntity
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class GetWeatherInfoUseCase (
    private val repository: WeatherRepository,
    defaultDispatcher: CoroutineDispatcher
) : SuspendUseCase<WeatherInfoModel.Request, ResultEntity<WeatherInfoModel.Response>>(
    defaultDispatcher
) {

    override suspend fun execute(parameters: WeatherInfoModel.Request) =
        repository.getWeatherInfo(parameters)

}