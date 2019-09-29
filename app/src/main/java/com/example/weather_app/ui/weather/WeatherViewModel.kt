package com.example.weather_app.ui.weather

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.WeatherRepository
import com.example.weather_app.data.entity.DisplayableWeatherInfo
import com.example.weather_app.extensions.toFavoriteCity
import com.example.weather_app.ui.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
        app: Application,
        private val weatherRepository: WeatherRepository
) : BaseViewModel(app) {

    val weatherInfo = MutableLiveData<DisplayableWeatherInfo>()
    val message = MutableLiveData<String>() //todo change to single event live data
    private val mapIsReady = MutableLiveData<Boolean>()

    fun getWeather(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            try {
                val response = weatherRepository.getWeather(query)
                val isFavoriteCity = weatherRepository.isCityAdded(response.location.name, response.location.lat, response.location.lng)
                weatherInfo.postValue(
                        DisplayableWeatherInfo(
                                response.location.name,
                                response.location.region,
                                response.location.country,
                                response.location.lat,
                                response.location.lng,
                                response.currentWeather.lastUpdated,
                                response.currentWeather.condition.text,
                                response.currentWeather.tempC.toString(),
                                response.currentWeather.windKph.toString(),
                                response.currentWeather.pressureIn.toString(),
                                response.currentWeather.humidity.toString(),
                                isFavoriteCity
                        )
                )
            } catch (e: Exception) {
                Log.e("Logos", "city was not found", e)
            } finally {
            }
            loading.postValue(false)
        }
    }

    fun setMapIsReady() {
        mapIsReady.value = true
        weatherInfo.value = weatherInfo.value //todo force update
    }

    fun getWeatherByCoordinates(coordinates: LatLng?) {
        if (loading.value != true) {
            coordinates?.run {
                getWeather("$latitude, $longitude")
            }
        } else {
            message.value = "Please wait"
        }
    }

    fun checkFavoriteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfo.value?.let { info ->
                val isCityAdded = weatherRepository.isCityAdded("", info.lat, info.lng) //todo review logic
                with(info.toFavoriteCity()) {
                    if (isCityAdded) {
                        weatherRepository.deleteFavoriteCity(this)
                        info.isFavoriteCity = false
                        weatherInfo.postValue(info)
                    } else {
                        weatherRepository.addFavoriteCity(this)
                        info.isFavoriteCity = true
                        weatherInfo.postValue(info)
                    }
                }
            }
        }
    }

}
