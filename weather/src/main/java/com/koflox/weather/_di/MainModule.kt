package com.koflox.weather._di

import com.koflox.weather.domain.use_case.weather_info.WeatherInfoUseCase
import com.koflox.weather.domain.use_case.weather_info.WeatherInfoUseCaseImpl
import com.koflox.weather.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel {
        WeatherViewModel(
            app = get(),
            weatherInfoUseCase = get(),
            cityUseCase = get(),
        )
    }
}

internal val useCaseModule = module {
    factory<WeatherInfoUseCase> {
        WeatherInfoUseCaseImpl(
            repo = get(),
        )
    }
}
