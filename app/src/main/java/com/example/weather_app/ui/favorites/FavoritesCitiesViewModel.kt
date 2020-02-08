package com.example.weather_app.ui.favorites

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.Result
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.data.source.AppDataRepository
import com.example.weather_app.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoritesCitiesViewModel(
    app: Application,
    private val dataRepository: AppDataRepository
) : BaseViewModel(app) {

    var selectedCity: FavoriteCity? = null

    private val _favoriteCities = dataRepository.favoriteCities
    val favoriteCities = Transformations.map(_favoriteCities) {
        when (it) {
            is Result.Success -> it.data
            else -> emptyList()
        }
    }

    val showAddCityHint = Transformations.map(_favoriteCities) {
        it is Result.Success && it.data.isEmpty()
    }

    fun deleteFavoriteCity() {
        viewModelScope.launch {
            selectedCity?.run { dataRepository.delete(this) }
        }
    }

}
