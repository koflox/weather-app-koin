package com.koflox.weather.ui.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.koflox.common_ui.Event
import com.koflox.common_ui.base.BaseViewModel
import com.koflox.weather.R
import com.koflox.weather.WeatherCityProvider
import com.koflox.weather.domain.entity.City
import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.SystemOfMeasurement
import com.koflox.weather.domain.use_case.weather_info.WeatherInfoUseCase
import com.koflox.weather.domain.use_case.weather_info.WeatherQueryParam
import com.koflox.weather.ui.displayed.DisplayedWeatherItem
import com.koflox.weather.ui.displayed.WeatherData
import com.koflox.weather.ui.displayed.toDetailsWeatherData
import com.koflox.weather.ui.displayed.toForecastWeatherData
import com.koflox.weather.ui.displayed.toHourlyWeatherData
import com.koflox.weather.ui.displayed.toMainWeatherData
import com.koflox.weather.ui.displayed.toPrecipitationWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.koflox.common_android_res.R as commonResR

internal class WeatherViewModel(
    private val app: Application,
    private val weatherInfoUseCase: WeatherInfoUseCase,
    private val cityUseCase: WeatherCityProvider,
) : BaseViewModel(app) {

    companion object {
        private const val TIME_PATTERN_HOURLY_WEATHER_DATA = "ha"
        private const val TIME_PATTERN_PRECIPITATION_WEATHER_DATA = "ha"
        private const val TIME_PATTERN_MAIN_WEATHER_DATA = "EEE, h:mm a"
        private const val TIME_PATTERN_FORECAST_DATA = "EEE"

        private const val SEGMENT_COUNT_HOURLY_WEATHER_DATA = 8
        private const val SEGMENT_COUNT_PRECIPITATION_WEATHER_DATA = 8
        private const val SEGMENT_COUNT_FORECAST_WEATHER_DATA = 5
    }

    private val dataUnit = SystemOfMeasurement.METRIC

    private lateinit var city: City

    private val _weatherData = MutableLiveData<List<WeatherData>>()
    private val _displayedToolbarInfo = MutableLiveData<Pair<String, String>>()
    private val _message = MutableLiveData<Event<String>>()
    private val _isCityAddedToFavorite = MutableLiveData<Event<Boolean>>()

    val weatherData: LiveData<List<WeatherData>> = _weatherData
    val displayedToolbarInfo: LiveData<Pair<String, String>> = _displayedToolbarInfo
    val message: LiveData<Event<String>> = _message
    val isCityAddedToFavorite: LiveData<Event<Boolean>> = _isCityAddedToFavorite

    fun getCurrentWeather(
        searchQuery: String? = null,
        cityIdToSearch: String? = null,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)

            val forecastResult: Result<Forecast>
            val currentWeatherResult: Result<CurrentWeather>
            when {
                !searchQuery.isNullOrBlank() -> {
                    val param = WeatherQueryParam.Place(searchQuery)
                    forecastResult = weatherInfoUseCase.getForecast(param, dataUnit)
                    currentWeatherResult = weatherInfoUseCase.getCurrentWeather(param, dataUnit)
                }

                !cityIdToSearch.isNullOrBlank() -> {
                    val param = WeatherQueryParam.City(cityIdToSearch)
                    forecastResult = weatherInfoUseCase.getForecast(param, dataUnit)
                    currentWeatherResult = weatherInfoUseCase.getCurrentWeather(param, dataUnit)
                }

                else -> return@launch
            }
            when {
                forecastResult.isSuccess && currentWeatherResult.isSuccess -> {
                    val currentWeather = currentWeatherResult.getOrThrow()
                    displayWeather(currentWeather, forecastResult.getOrThrow())
                    city = currentWeather.city
                    checkFavoriteCity(city)
                }

                forecastResult.isFailure -> displayError()
                currentWeatherResult.isFailure -> displayError()
            }
        }
    }

    fun onAddDeleteOptionClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val isCityAdded = cityUseCase.isCityAdded(city.id)
                .getOrNull() ?: return@launch
            when {
                isCityAdded -> cityUseCase.deleteCity(city.id)
                else -> cityUseCase.addCity(city)
            }
            _isCityAddedToFavorite.postValue(Event(!isCityAdded))
        }
    }

    private fun displayWeather(
        currentWeather: CurrentWeather,
        forecast: Forecast,
    ) {
        //create current weather data
        val currentWeatherData = currentWeather.toMainWeatherData(TIME_PATTERN_MAIN_WEATHER_DATA, dataUnit)

        //create hourly weather data
        val hourlyDataTitle = app.getString(R.string.title_hourly_weather_data)
        val currentHourWeatherData =
            DisplayedWeatherItem(app.getString(R.string.text_time_now), currentWeather.main.temp.toInt())
        val hourlyWeatherData = forecast.toHourlyWeatherData(
            TIME_PATTERN_HOURLY_WEATHER_DATA,
            SEGMENT_COUNT_HOURLY_WEATHER_DATA,
            hourlyDataTitle,
            currentHourWeatherData
        )

        //create details weather data
        val detailsWeatherDataTitle = app.getString(R.string.title_details_weather_data)
        val detailsWeatherData = currentWeather.toDetailsWeatherData(detailsWeatherDataTitle, dataUnit)

        //create precipitation weather data
        val precipitationValue = when {
            currentWeather.rain != null -> currentWeather.rain.h.toInt()
            currentWeather.snow != null -> currentWeather.snow.h.toInt()
            else -> 0
        }
        val currentPrecipitationWeatherData = DisplayedWeatherItem(app.getString(R.string.text_time_now), precipitationValue)
        val precipitationWeatherDataTitle = app.getString(R.string.title_precipitation_weather_data)
        val precipitationWeatherData = forecast.toPrecipitationWeatherData(
            TIME_PATTERN_PRECIPITATION_WEATHER_DATA,
            SEGMENT_COUNT_PRECIPITATION_WEATHER_DATA,
            precipitationWeatherDataTitle,
            currentPrecipitationWeatherData
        )

        //create forecast data
        val forecastDataTitle = app.getString(R.string.title_forecast_weather_data)
        val forecastData = forecast.toForecastWeatherData(TIME_PATTERN_FORECAST_DATA, forecastDataTitle)

        val displayedWeather = listOf(
            currentWeatherData,
            hourlyWeatherData,
            detailsWeatherData,
            precipitationWeatherData,
            forecastData
        )
        _loading.postValue(false)
        _weatherData.postValue(displayedWeather)
        _displayedToolbarInfo.postValue(Pair(currentWeather.city.name, currentWeatherData.dayName))
    }

    private fun checkFavoriteCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            val isCityAdded = cityUseCase.isCityAdded(city.id)
                .getOrNull() ?: return@launch
            _isCityAddedToFavorite.postValue(Event(isCityAdded))
        }
    }

    private fun displayError() {
        _displayedToolbarInfo.postValue(Pair(app.getString(commonResR.string.text_common_error), ""))
        _loading.postValue(false)
        _weatherData.postValue(listOf())
    }

}
