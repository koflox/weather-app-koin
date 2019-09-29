package com.example.weather_app.di

import android.content.Context
import androidx.room.Room
import com.example.weather_app.data.WeatherRepository
import com.example.weather_app.data.db.WeatherDatabase
import org.koin.dsl.module

val dataModule = module {
    single { createWeatherDatabase(get()) }
    single { WeatherRepository(get(), get(), get()) }
}

fun createWeatherDatabase(context: Context): WeatherDatabase = Room.databaseBuilder(
        context.applicationContext,
        WeatherDatabase::class.java,
        "weather_db"
).fallbackToDestructiveMigration().build()