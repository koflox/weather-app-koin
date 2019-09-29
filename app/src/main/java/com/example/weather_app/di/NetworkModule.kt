package com.example.weather_app.di

import com.example.weather_app.BuildConfig
import com.example.weather_app.data.network.ApixuService
import com.example.weather_app.data.network.AuthInterceptor
import com.example.weather_app.data.network.PixabayService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createLoggingInterceptor() }

    single(named("apixuAuthInterceptor")) { createAuthInterceptor(BuildConfig.APIXU_API_KEY) }
    single(named("pixabayAuthInterceptor")) { createAuthInterceptor(BuildConfig.PIXABAY_API_KEY) }

    single(named("apixuHttpClient")) { createOkHttpClient(get(), get(named("apixuAuthInterceptor"))) }
    single(named("pixabayHttpClient")) { createOkHttpClient(get(), get(named("pixabayAuthInterceptor"))) }

    single { createWebService<ApixuService>(get(named("apixuHttpClient")), BuildConfig.APIXU_API_URL) }
    single { createWebService<PixabayService>(get(named("pixabayHttpClient")), BuildConfig.PIXABAY_API_URL) }
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

private fun createAuthInterceptor(apiKey: String): AuthInterceptor =
    AuthInterceptor(apiKey)

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    return retrofit.create(T::class.java)
}