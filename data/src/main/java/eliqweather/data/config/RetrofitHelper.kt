package eliqweather.data.config

import eliqweather.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author: Shahab Azimi
 * @Date: 2023 - 09 - 02
 **/
class RetrofitHelper() {

    private lateinit var httpClient: OkHttpClient

    private lateinit var bankRetrofit: Retrofit

    private lateinit var serverService: ServerService

    init {
        configRetrofit()
    }

    private fun configRetrofit(
    ) {
        initHttpClient()
        initRetrofit()
        initBankRemoteService()
    }

    private fun initHttpClient(isFollowRedirect: Boolean = true) {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

        httpClient = OkHttpClient.Builder().apply {
            addInterceptor(logging)
            followRedirects(isFollowRedirect)

            readTimeout(BuildConfig.TIME_OUT.toLong(), TimeUnit.SECONDS)
            writeTimeout(BuildConfig.TIME_OUT.toLong(), TimeUnit.SECONDS)
            connectTimeout(BuildConfig.TIME_OUT.toLong(), TimeUnit.SECONDS)
        }.build()
    }

    private fun initRetrofit() {
        bankRetrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initBankRemoteService() {
        serverService = bankRetrofit.create(ServerService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com/v1"
    }
}