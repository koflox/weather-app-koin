package com.example.weather_app._di

import com.example.weather_app.CitiesNavigatorImpl
import com.example.weather_app.WeatherCityProviderImpl
import com.koflox.cities.CitiesNavigator
import com.koflox.cities.favorites.FavoritesCitiesViewModel
import com.koflox.cities.search.SearchViewModel
import com.koflox.weather.WeatherCityProvider
import com.koflox.weather._di.weatherModules
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        FavoritesCitiesViewModel(
            dataRepository = get(),
        )
    }
    viewModel {
        SearchViewModel()
    }
}

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
    viewModelModule,
    dataModule,
    navigation,
    weatherModuleInjector,
) + weatherModules
