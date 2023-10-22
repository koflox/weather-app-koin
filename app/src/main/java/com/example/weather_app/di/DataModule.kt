package com.example.weather_app.di

import android.content.Context
import androidx.room.Room
import com.koflox.cities.data.source.CityLocalDataSource
import com.koflox.cities.data.source.CityRepository
import com.koflox.cities.data.source.CityRepositoryImpl
import com.koflox.cities.data.source.local.CityDatabase
import com.koflox.cities.data.source.local.CityLocalDataSourceImpl
import com.koflox.weather.data.source.WeatherRemoteDataSource
import com.koflox.weather.data.source.WeatherRepository
import com.koflox.weather.data.source.WeatherRepositoryImpl
import com.koflox.weather.data.source.remote.WeatherRemoteDataSourceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    factory { createCityDatabase(get()) }
    factory<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImpl(
            openWeatherMapService = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }
    factory {
        get<CityDatabase>().favoriteCitiesDao
    }
    factory<CityLocalDataSource> {
        CityLocalDataSourceImpl(
            favoriteCitiesDao = get(),
            ioDispatcher = Dispatchers.IO,
        )
    }
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            remoteDataSource = get(),
        )
    }
    single<CityRepository> {
        CityRepositoryImpl(
            localDataSource = get(),
        )
    }
}

fun createCityDatabase(context: Context): CityDatabase = Room.databaseBuilder(
    context.applicationContext,
    CityDatabase::class.java,
    "city_db"
).fallbackToDestructiveMigration().build()
