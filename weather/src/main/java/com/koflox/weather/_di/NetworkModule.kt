package com.koflox.weather._di

import com.koflox.weather.BuildConfig
import com.koflox.weather.data.source.remote.AuthInterceptor
import com.koflox.weather.data.source.remote.OpenWeatherMapService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val networkModule = module {
    val apiKeyParamOpenWeatherMap = "appid"
    val loggingInterceptorOpenWeatherMap = "loggingInterceptorOpenWeatherMap"
    val authInterceptorOpenWeatherMap = "authInterceptorOpenWeatherMap"
    val httpClientOpenWeatherMap = "httpClientOpenWeatherMap"

    factory<Interceptor>(named(loggingInterceptorOpenWeatherMap)) { createLoggingInterceptor() }
    factory<Interceptor>(named(authInterceptorOpenWeatherMap)) {
        AuthInterceptor(
            apiKeyParam = apiKeyParamOpenWeatherMap,
            apiKeyValue = BuildConfig.API_KEY_OPEN_WEATHER_MAP,
        )
    }
    factory(named(httpClientOpenWeatherMap)) {
        createOkHttpClient(
            httpLoggingInterceptor = get(named(loggingInterceptorOpenWeatherMap)),
            authInterceptor = get(named(authInterceptorOpenWeatherMap)),
        )
    }
    factory<OpenWeatherMapService> {
        createWebService(
            okHttpClient = get(named(httpClientOpenWeatherMap)),
            url = OpenWeatherMapService.BASE_URL_API,
        )
    }
}

private fun createOkHttpClient(
    httpLoggingInterceptor: Interceptor,
    authInterceptor: Interceptor,
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .addInterceptor(authInterceptor)
    .build()

private fun createLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}
