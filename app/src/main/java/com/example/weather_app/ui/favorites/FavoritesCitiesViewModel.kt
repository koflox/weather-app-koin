package com.example.weather_app.ui.favorites

import android.app.Application
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.WeatherRepository
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesCitiesViewModel(
    app: Application,
    private val weatherRepository: WeatherRepository
) : BaseViewModel(app) {

    var selectedCity: FavoriteCity? = null

    val favoriteCities = weatherRepository.favoriteCities

    val showAddCityHint = Transformations.map(favoriteCities) {
        it.isEmpty()
    }

    fun deleteFavoriteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedCity?.run { weatherRepository.deleteFavoriteCity(this) }
        }
    }

}
