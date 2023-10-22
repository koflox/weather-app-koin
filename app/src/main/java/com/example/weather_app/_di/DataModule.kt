package com.example.weather_app._di

import android.content.Context
import androidx.room.Room
import com.koflox.cities.data.source.CityLocalDataSource
import com.koflox.cities.data.source.CityRepository
import com.koflox.cities.data.source.CityRepositoryImpl
import com.koflox.cities.data.source.local.CityDatabase
import com.koflox.cities.data.source.local.CityLocalDataSourceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    factory { createCityDatabase(get()) }
    factory {
        get<CityDatabase>().favoriteCitiesDao
    }
    factory<CityLocalDataSource> {
        CityLocalDataSourceImpl(
            favoriteCitiesDao = get(),
            ioDispatcher = Dispatchers.IO,
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
