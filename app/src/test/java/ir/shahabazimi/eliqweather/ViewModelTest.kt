package ir.shahabazimi.eliqweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eliqweather.data.utils.getOrAwaitForResult
import eliqweather.domain.models.ResultEntity
import eliqweather.domain.models.WeatherInfoModel
import eliqweather.domain.usecase.GetWeatherInfoUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import ir.shahabazimi.eliqweather.presentation.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 06
 **/
@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ViewModelTest {

    private lateinit var viewModel: WeatherViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var weatherInfoUseCase: GetWeatherInfoUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        viewModel = WeatherViewModel(
            weatherInfoUseCase
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Check Offline Weather Response`() = runTest(testDispatcher) {
        val request = WeatherInfoModel.Request(
            isOnline = false,
            latitude = 0.0,
            longitude = 0.0
        )
        val expectedResponse = WeatherInfoModel.Response(
            latitude = 57.703068,
            longitude = 11.889999
        )

        coEvery {
            weatherInfoUseCase(request)
        } returns ResultEntity.Success(expectedResponse)


        val response = viewModel.response.getOrAwaitForResult {
            viewModel.getWeatherInfo()
        }
        assert(response == expectedResponse)

    }

    @Test
    fun `Check Online Weather Response`() = runTest(testDispatcher) {
        val request = WeatherInfoModel.Request(
            isOnline = true,
            latitude = 53.22,
            longitude = 12.44
        )
        val expectedResponse = WeatherInfoModel.Response(
            latitude = 53.22,
            longitude = 12.44
        )

        coEvery {
            weatherInfoUseCase(request)
        } returns ResultEntity.Success(expectedResponse)


        val response = viewModel.response.getOrAwaitForResult {
            viewModel.getWeatherInfo()
        }
        assert(response == expectedResponse)

    }


}