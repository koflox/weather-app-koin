package com.example.weather_app._di

import com.example.weather_app.CitiesNavigatorImpl
import com.example.weather_app.WeatherCityProviderImpl
import com.koflox.cities.CitiesNavigator
import com.koflox.cities._di.cityModules
import com.koflox.weather.WeatherCityProvider
import com.koflox.weather._di.weatherModules
import org.koin.core.module.Module
import org.koin.dsl.module

private val navigation = module {
    factory<CitiesNavigator> {
        CitiesNavigatorImpl()
    }
}

private val weatherModuleInjector = module {
    factory<WeatherCityProvider> {
        WeatherCityProviderImpl(
            cityRepository = get(),
        )
    }
}

internal val appModules: List<Module> = listOf(
    navigation,
    weatherModuleInjector,
) + weatherModules + cityModules
