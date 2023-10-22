package com.example.weather_app

import android.app.Application
import com.example.weather_app.di.weatherModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(weatherModules)
        }
    }
}
