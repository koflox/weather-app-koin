package com.example.weather_app.di

import com.example.weather_app.CitiesNavigatorImpl
import com.example.weather_app.WeatherCityUseCaseImpl
import com.koflox.cities.CitiesNavigator
import com.koflox.cities.favorites.FavoritesCitiesViewModel
import com.koflox.cities.search.SearchViewModel
import com.koflox.weather.WeatherCityUseCase
import com.koflox.weather.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WeatherViewModel(
            app = get(),
            dataRepository = get(),
            cityUseCase = get(),
        )
    }
    viewModel {
        FavoritesCitiesViewModel(
            app = get(),
            dataRepository = get(),
        )
    }
    viewModel {
        SearchViewModel(
            app = get(),
        )
    }
}

private val navigation = module {
    factory<CitiesNavigator> {
        CitiesNavigatorImpl()
    }
}

private val weatherModuleInjector = module {
    factory<WeatherCityUseCase> {
        WeatherCityUseCaseImpl(
            cityRepository = get(),
        )
    }
}

internal val weatherModules = listOf(
    viewModelModule,
    networkModule,
    dataModule,
    navigation,
    weatherModuleInjector,
)
