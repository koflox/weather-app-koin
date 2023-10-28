package com.koflox.cities.cities

import androidx.lifecycle.ViewModel
import com.koflox.cities.R
import com.koflox.cities.data.data.FavoriteCity
import com.koflox.cities.data.source.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import com.koflox.common_android_res.R as commonResR

internal class CitiesViewModel(
    private val dataRepository: CityRepository,
) : ViewModel() {

    val citiesUiState: Flow<CitiesUiState> = flow {
        emit(CitiesUiState.Loading(titleResId = commonResR.string.text_loading))
        emitAll(
            dataRepository.observeCities().map { cities ->
                CitiesUiState.Data(
                    titleResId = R.string.title_saved_cities,
                    models = mapCityUiModels(cities),
                )
            }
        )
    }

    private fun mapCityUiModels(cities: List<FavoriteCity>): List<CityUiModel> = cities.map { city ->
        CityUiModel(
            id = city.id,
            displayedName = "${city.cityName}, ${city.country}",
        )
    }

}
