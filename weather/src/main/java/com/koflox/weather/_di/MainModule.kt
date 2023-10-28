package com.koflox.weather._di

import com.koflox.weather.domain.use_case.weather_info.WeatherInfoUseCase
import com.koflox.weather.domain.use_case.weather_info.WeatherInfoUseCaseImpl
import com.koflox.weather.ui.weather.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { (searchQuery: String?, cityIdToSearch: String?) ->
        WeatherViewModel(
            searchQuery = searchQuery,
            cityIdToSearch = cityIdToSearch,
            dispatcherDefault = Dispatchers.Default,
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
