package com.example.weather_app.ui.current_weather

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_app.data.WeatherRepository
import com.example.weather_app.data.displayed.WeatherData
import com.example.weather_app.data.entity.DisplayableWeatherInfo
import com.example.weather_app.data.response.open_weather_map.current_weather.toDetailsWeatherData
import com.example.weather_app.data.response.open_weather_map.current_weather.toMainWeatherData
import com.example.weather_app.data.response.open_weather_map.forecast.toForecastWeatherData
import com.example.weather_app.data.response.open_weather_map.forecast.toHourlyWeatherData
import com.example.weather_app.data.response.open_weather_map.forecast.toPrecipitationWeatherData
import com.example.weather_app.ui.base.BaseViewModel
import com.example.weather_app.util.formatToLocalTime
import com.example.weather_app.util.toFavoriteCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CurrentWeatherViewModel(
        app: Application,
        private val weatherRepository: WeatherRepository
) : BaseViewModel(app) {

    companion object {
        private const val TIME_PATTERN_HOURLY_WEATHER_DATA = "ha"
        private const val TIME_PATTERN_PRECIPITATION_WEATHER_DATA = "ha"
        private const val TIME_PATTERN_MAIN_WEATHER_DATA = "EEE, h:mm a"

        private const val SEGMENT_COUNT_HOURLY_WEATHER_DATA = 8
        private const val SEGMENT_COUNT_PRECIPITATION_WEATHER_DATA = 8
        private const val SEGMENT_COUNT_FORECAST_WEATHER_DATA = 5
    }

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    val weatherData: LiveData<List<WeatherData>>
        get() = _weatherData

    private val searchHandler = Handler()
    private val searchRunnable = Runnable {
        getCurrentWeather()
    }
    private val SEARCH_DELAY = 450L
    private lateinit var searchQuery: String

    val weatherInfo = MutableLiveData<DisplayableWeatherInfo>()
    val message = MutableLiveData<String>() //todo change to single event live data
    private val mapIsReady = MutableLiveData<Boolean>()

    init {
        searchQuery = "warsaw"
        getCurrentWeather()
    }

    override fun onCleared() {
        searchHandler.removeCallbacks(searchRunnable)
    }

    @SuppressLint("DefaultLocale")
    private fun getCurrentWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            try {
                val forecastResponse = weatherRepository.getForecast(searchQuery)
                Log.d("Logos", "forecastResponse:\n$forecastResponse")
                val currentWeatherResponse = weatherRepository.getWeather(searchQuery)
                Log.d("Logos", "currentWeatherResponse:\n$currentWeatherResponse")


                currentWeatherResponse.dt
                        .formatToLocalTime(TIME_PATTERN_MAIN_WEATHER_DATA, currentWeatherResponse.timezone)
                        .toLowerCase(Locale.getDefault())
                        .capitalize()

                val currentHourWeatherData = Pair("NOW", currentWeatherResponse.main.temp.toInt())
                val hourlyWeatherData = forecastResponse.toHourlyWeatherData(
                        TIME_PATTERN_HOURLY_WEATHER_DATA,
                        SEGMENT_COUNT_HOURLY_WEATHER_DATA,
                        currentHourWeatherData)


                val precipitationValue = when {
                    currentWeatherResponse.rain != null -> currentWeatherResponse.rain.h.toInt()
                    currentWeatherResponse.snow != null -> currentWeatherResponse.snow.h.toInt()
                    else -> 0
                }
                val currentPrecipitationWeatherData = Pair("NOW", precipitationValue)
                val precipitationWeatherData = forecastResponse.toPrecipitationWeatherData(
                        TIME_PATTERN_PRECIPITATION_WEATHER_DATA,
                        SEGMENT_COUNT_PRECIPITATION_WEATHER_DATA,
                        currentPrecipitationWeatherData)


                _weatherData.postValue(listOf(
                        currentWeatherResponse.toMainWeatherData(),
                        hourlyWeatherData,
                        currentWeatherResponse.toDetailsWeatherData(),
                        precipitationWeatherData,
                        forecastResponse.toForecastWeatherData()
                ))
            } catch (e: Exception) {
                Log.e("Logos", "city was not found", e)
            }
            loading.postValue(false)
        }
    }

    fun setMapIsReady() {
        mapIsReady.value = true
        weatherInfo.value = weatherInfo.value //todo force update
    }

//    fun getWeatherByCoordinates(coordinates: LatLng?) {
//        if (loading.value != true) {
//            coordinates?.run {
//                getCurrentWeather("$latitude, $longitude")
//            }
//        } else {
//            message.value = "Please wait"
//        }
//    }

    fun checkFavoriteCity() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfo.value?.let { info ->
                val isCityAdded =
                        weatherRepository.isCityAdded("", info.lat, info.lng) //todo review logic
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

    fun onQueryTextChange(query: String) {
        searchQuery = query.also { q ->
            searchHandler.run {
                removeCallbacks(searchRunnable)
                if (q.isNotEmpty())
                    postDelayed(searchRunnable, SEARCH_DELAY)
            }
        }
    }

}
