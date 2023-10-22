package com.example.weather_app.di

import com.example.weather_app.BuildConfig
import com.koflox.weather.data.source.remote.AuthInterceptor
import com.koflox.weather.data.source.remote.OpenWeatherMapService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory<Interceptor> { createLoggingInterceptor() }

    val apiKeyParamPixabay = "key"
    val apiKeyParamOpenWeatherMap = "appid"

    val authInterceptorPixabay = "authInterceptorPixabay"
    val authInterceptorOpenWeatherMap = "authInterceptorOpenWeatherMap"

    val httpClientPixabay = "httpClientPixabay"
    val httpClientOpenWeatherMap = "httpClientOpenWeatherMap"

    factory<Interceptor>(named(authInterceptorPixabay)) {
        createAuthInterceptor(
            apiKeyParamPixabay,
            BuildConfig.API_KEY_PIXABAY
        )
    }
    factory<Interceptor>(named(authInterceptorOpenWeatherMap)) {
        createAuthInterceptor(
            apiKeyParamOpenWeatherMap,
            BuildConfig.API_KEY_OPEN_WEATHER_MAP
        )
    }

    factory<OkHttpClient>(named(httpClientPixabay)) {
        createOkHttpClient(
            get(),
            get(named(authInterceptorPixabay))
        )
    }
    factory<OkHttpClient>(named(httpClientOpenWeatherMap)) {
        createOkHttpClient(
            httpLoggingInterceptor = get(),
            authInterceptor = get(named(authInterceptorOpenWeatherMap)),
        )
    }

//    factory<com.koflox.photos.data.source.remote.PixabayService> {
//        createWebService<com.koflox.photos.data.source.remote.PixabayService>(
//            get(named(httpClientPixabay)),
//            "https://pixabay.com/api/"
//        )
//    }
    factory<OpenWeatherMapService> {
        createWebService<OpenWeatherMapService>(
            get(named(httpClientOpenWeatherMap)),
            "https://api.openweathermap.org/data/2.5/",
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

private fun createAuthInterceptor(apiKeyParam: String, apiKeyValue: String) =
    AuthInterceptor(apiKeyParam, apiKeyValue)

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}