package com.koflox.cities.favorites

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.koflox.cities.data.data.FavoriteCity
import com.koflox.cities.data.source.CityRepository
import com.koflox.common_jvm_util.Result
import com.koflox.common_ui.Event
import com.koflox.common_ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoritesCitiesViewModel(
    app: Application,
    private val dataRepository: CityRepository,
) : BaseViewModel(app) {

    private val _selectedCity = MutableLiveData<Event<Triple<FavoriteCity, Int, Boolean>>>()
    val selectedCity: LiveData<Event<Triple<FavoriteCity, Int, Boolean>>> = _selectedCity

    private val _onGetWeather = MutableLiveData<Event<Int>>()
    val onGetWeather: LiveData<Event<Int>> = _onGetWeather

    val favoriteCities = dataRepository.observeFavoriteCities().map {
        when (it) {
            is Result.Success -> it.data
            else -> emptyList()
        }
    }

    fun onActionRemove() {
        viewModelScope.launch {
            val (city, _) = selectedCity.value?.peekContent() ?: return@launch
            dataRepository.delete(city.id)
        }
    }

    fun onCitySelected(city: FavoriteCity, showContextMenu: Boolean) {
        val position = favoriteCities.value?.indexOf(city) ?: return
        when {
            position != -1 -> _selectedCity.value = Event(Triple(city, position, showContextMenu))
            else -> return
        }
    }

    fun onActionGetWeather() {
        val cityId = selectedCity.value?.peekContent()?.first?.id ?: return
        _onGetWeather.value = Event(cityId)
    }

}
