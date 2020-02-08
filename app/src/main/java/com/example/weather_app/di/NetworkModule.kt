package com.example.weather_app.di

import com.example.weather_app.BuildConfig
import com.example.weather_app.data.source.remote.AuthInterceptor
import com.example.weather_app.data.source.remote.OpenWeatherMapService
import com.example.weather_app.data.source.remote.PixabayService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createLoggingInterceptor() }

    val apiKeyParamPixabay = "key"
    val apiKeyParamOpenWeatherMap = "appid"

    val authInterceptorPixabay = "authInterceptorPixabay"
    val authInterceptorOpenWeatherMap = "authInterceptorOpenWeatherMap"

    val httpClientPixabay = "httpClientPixabay"
    val httpClientOpenWeatherMap = "httpClientOpenWeatherMap"

    single(named(authInterceptorPixabay)) {
        createAuthInterceptor(
                apiKeyParamPixabay,
                BuildConfig.API_KEY_PIXABAY
        )
    }
    single(named(authInterceptorOpenWeatherMap)) {
        createAuthInterceptor(
                apiKeyParamOpenWeatherMap,
                BuildConfig.API_KEY_OPEN_WEATHER_MAP
        )
    }

    single(named(httpClientPixabay)) {
        createOkHttpClient(
                get(),
                get(named(authInterceptorPixabay))
        )
    }
    single(named(httpClientOpenWeatherMap)) {
        createOkHttpClient(
                get(),
                get(named(authInterceptorOpenWeatherMap))
        )
    }

    single {
        createWebService<PixabayService>(
                get(named(httpClientPixabay)),
                BuildConfig.BASE_URL_PIXABAY
        )
    }
    single {
        createWebService<OpenWeatherMapService>(
                get(named(httpClientOpenWeatherMap)),
                BuildConfig.BASE_URL_OPEN_WEATHER_MAP
        )
    }
}

private fun createOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor
): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .addInterceptor(authInterceptor)
    .build()

private fun createLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}

private fun createAuthInterceptor(apiKeyParam: String, apiKeyValue: String) =
    AuthInterceptor(apiKeyParam, apiKeyValue)

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    return retrofit.create(T::class.java)
}