package com.example.weather_app.ui.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.Result
import com.example.weather_app.data.data.FavoriteCity
import com.example.weather_app.data.source.AppDataRepository
import com.example.weather_app.ui.base.BaseViewModel
import com.example.weather_app.util.Event
import kotlinx.coroutines.launch

class FavoritesCitiesViewModel(
    app: Application,
    private val dataRepository: AppDataRepository
) : BaseViewModel(app) {

    private val _selectedCity = MutableLiveData<Event<Triple<FavoriteCity, Int, Boolean>>>()
    val selectedCity: LiveData<Event<Triple<FavoriteCity, Int, Boolean>>> = _selectedCity

    private val _onGetWeather = MutableLiveData<Event<Int>>()
    val onGetWeather: LiveData<Event<Int>> = _onGetWeather

    private val _favoriteCities = dataRepository.favoriteCities
    val favoriteCities = Transformations.map(_favoriteCities) {
        when (it) {
            is Result.Success -> it.data
            else -> emptyList()
        }
    }

    fun onActionRemove() {
        viewModelScope.launch {
            val (city, _) = selectedCity.value?.peekContent() ?: return@launch
            dataRepository.delete(city)
        }
    }

    fun onCitySelected(city: FavoriteCity, showContextMenu: Boolean) {
        val position = favoriteCities.value?.indexOf(city) ?: return
        when {
            position != -1 -> _selectedCity.value = Event(Triple(city, 1, showContextMenu))
            else -> return
        }
    }

    fun onActionGetWeather() {
        val cityId = selectedCity.value?.peekContent()?.first?.id ?: return
        _onGetWeather.value = Event(cityId)
    }

}
