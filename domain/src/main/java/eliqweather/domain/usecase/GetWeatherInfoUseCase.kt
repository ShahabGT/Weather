package eliqweather.domain.usecase

import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.repository.WeatherRepository
import eliqweather.domain.utils.ResultEntity
import eliqweather.domain.utils.SuspendUseCase
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class GetWeatherInfoUseCase constructor(
    private val repository: WeatherRepository,
    defaultDispatcher: CoroutineDispatcher
) : SuspendUseCase<WeatherInfoModel.request, ResultEntity<WeatherInfoModel.response>>(
    defaultDispatcher
) {

    override suspend fun execute(parameters: WeatherInfoModel.request) =
        repository.getWeatherInfo(parameters)

}