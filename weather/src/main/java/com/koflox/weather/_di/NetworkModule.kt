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

private const val QUALIFIER_LOGGING_INTERCEPTOR = "QUALIFIER_LOGGING_INTERCEPTOR"
private const val QUALIFIER_AUTH_INTERCEPTOR = "QUALIFIER_AUTH_INTERCEPTOR"
private const val QUALIFIER_OKHTTP_CLIENT = "QUALIFIER_OKHTTP_CLIENT"

internal val networkModule = module {
    factory<Interceptor>(named(QUALIFIER_LOGGING_INTERCEPTOR)) {
        createLoggingInterceptor()
    }
    factory<Interceptor>(named(QUALIFIER_AUTH_INTERCEPTOR)) {
        AuthInterceptor(
            apiKeyParam = "appid",
            apiKeyValue = BuildConfig.API_KEY_OPEN_WEATHER_MAP,
        )
    }
    factory(named(QUALIFIER_OKHTTP_CLIENT)) {
        createOkHttpClient(
            httpLoggingInterceptor = get(named(QUALIFIER_LOGGING_INTERCEPTOR)),
            authInterceptor = get(named(QUALIFIER_AUTH_INTERCEPTOR)),
        )
    }
    factory<OpenWeatherMapService> {
        createWebService(
            okHttpClient = get(named(QUALIFIER_OKHTTP_CLIENT)),
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
