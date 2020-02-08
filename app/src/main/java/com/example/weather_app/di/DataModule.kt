package com.example.weather_app.di

import android.content.Context
import androidx.room.Room
import com.example.weather_app.data.source.AppDataRepository
import com.example.weather_app.data.source.local.LocalDataSource
import com.example.weather_app.data.source.local.WeatherDatabase
import com.example.weather_app.data.source.remote.RemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single { createWeatherDatabase(get()) }

    single(named("remoteDataSource")) {
        RemoteDataSource(get())
    }
    single(named("localDataSource")) {
        LocalDataSource(get<WeatherDatabase>().favoriteCitiesDao)
    }

    single {
        AppDataRepository(
            remoteDataSource = get(named("remoteDataSource")),
            localDataSource = get(named("localDataSource")),
            pixabayService = get()
        )
    }
}

fun createWeatherDatabase(context: Context): WeatherDatabase = Room.databaseBuilder(
    context.applicationContext,
    WeatherDatabase::class.java,
    "weather_db"
).fallbackToDestructiveMigration().build()