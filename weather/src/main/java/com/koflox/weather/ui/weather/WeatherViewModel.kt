package com.koflox.weather.ui.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.koflox.weather.R
import com.koflox.weather.WeatherCityProvider
import com.koflox.weather.domain.entity.City
import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.SystemOfMeasurement
import com.koflox.weather.domain.use_case.weather_info.WeatherInfoUseCase
import com.koflox.weather.domain.use_case.weather_info.WeatherQueryParam
import com.koflox.weather.ui.weather.displayed.DetailsWeatherUiModel
import com.koflox.weather.ui.weather.displayed.DisplayedWeatherItem
import com.koflox.weather.ui.weather.displayed.ForecastWeatherUiModel
import com.koflox.weather.ui.weather.displayed.HourlyWeatherUiModel
import com.koflox.weather.ui.weather.displayed.MainWeatherUiModel
import com.koflox.weather.ui.weather.displayed.PrecipitationWeatherUiModel
import com.koflox.weather.ui.weather.displayed.WeatherUiState
import com.koflox.weather.ui.weather.displayed.toDetailsWeatherData
import com.koflox.weather.ui.weather.displayed.toForecastWeatherData
import com.koflox.weather.ui.weather.displayed.toHourlyWeatherData
import com.koflox.weather.ui.weather.displayed.toMainWeatherUiModel
import com.koflox.weather.ui.weather.displayed.toPrecipitationWeatherData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.koflox.common_android_res.R as commonResR

internal class WeatherViewModel(
    searchQuery: String? = null,
    cityIdToSearch: String? = null,
    private val app: Application,
    private val weatherInfoUseCase: WeatherInfoUseCase,
    private val cityUseCase: WeatherCityProvider,
) : AndroidViewModel(app) {

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

    private val _weatherUiState: MutableStateFlow<WeatherUiState> = MutableStateFlow(
        WeatherUiState.Loading(titleResId = commonResR.string.text_loading)
    )
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState

    init {
        getCurrentWeather(
            searchQuery = searchQuery,
            cityIdToSearch = cityIdToSearch,
        )
    }

    private fun getCurrentWeather(
        searchQuery: String? = null,
        cityIdToSearch: String? = null,
    ) {
        viewModelScope.launch {
            val param: WeatherQueryParam = when {
                !searchQuery.isNullOrBlank() -> WeatherQueryParam.Place(searchQuery)
                !cityIdToSearch.isNullOrBlank() -> WeatherQueryParam.City(cityIdToSearch)
                else -> return@launch
            }
            val forecastDef = async { weatherInfoUseCase.getForecast(param, dataUnit) }
            val currentWeatherDef = async { weatherInfoUseCase.getCurrentWeather(param, dataUnit) }
            val forecastResult = forecastDef.await()
            val currentWeatherResult = currentWeatherDef.await()
            when {
                forecastResult.isSuccess && currentWeatherResult.isSuccess -> {
                    val currentWeather = currentWeatherResult.getOrThrow()
                    city = currentWeather.city
                    val state = getWeatherState(currentWeather, forecastResult.getOrThrow())
                    _weatherUiState.emit(state)
                }

                forecastResult.isFailure -> displayError()
                currentWeatherResult.isFailure -> displayError()
            }
        }
    }

    fun onAddDeleteOptionClick() {
        viewModelScope.launch {
            val (isFavoriteCity, isFavoriteCityOptionVisible) = isFavoriteCity(city.id)
            if (isFavoriteCityOptionVisible.not()) return@launch
            val result = if (isFavoriteCity) {
                cityUseCase.deleteCity(city.id)
            } else {
                cityUseCase.addCity(city)
            }
            if (result.isSuccess) {
                _weatherUiState.update { state ->
                    if (state !is WeatherUiState.Data) {
                        state
                    } else {
                        state.copy(
                            isFavoriteCity = isFavoriteCity.not()
                        )
                    }
                }
            } else {
                // show error
            }
        }
    }

    private suspend fun getWeatherState(
        currentWeather: CurrentWeather,
        forecast: Forecast,
    ): WeatherUiState {
        val mainWeatherUiModel = getMainWeatherUiModel(currentWeather)
        val (isFavoriteCity, isFavoriteCityOptionVisible) = isFavoriteCity(currentWeather.city.id)
        return WeatherUiState.Data(
            weatherUiModels = listOf(
                mainWeatherUiModel,
                getHourlyWeatherUiModel(currentWeather, forecast),
                getDetailsWeatherUiModel(currentWeather),
                getPrecipitationWeatherUiModel(currentWeather, forecast),
                getForecastWeatherUiModel(forecast),
            ),
            title = currentWeather.city.name,
            subtitle = mainWeatherUiModel.dayName,
            isFavoriteCity = isFavoriteCity,
            isFavoriteCityOptionVisible = isFavoriteCityOptionVisible,
        )
    }

    private fun getMainWeatherUiModel(currentWeather: CurrentWeather): MainWeatherUiModel {
        return currentWeather.toMainWeatherUiModel(TIME_PATTERN_MAIN_WEATHER_DATA, dataUnit)
    }

    private fun getHourlyWeatherUiModel(
        currentWeather: CurrentWeather,
        forecast: Forecast,
    ): HourlyWeatherUiModel {
        val hourlyDataTitle = app.getString(R.string.title_hourly_weather_data)
        val currentHourWeatherData =
            DisplayedWeatherItem(app.getString(R.string.text_time_now), currentWeather.main.temp.toInt())
        return forecast.toHourlyWeatherData(
            TIME_PATTERN_HOURLY_WEATHER_DATA,
            SEGMENT_COUNT_HOURLY_WEATHER_DATA,
            hourlyDataTitle,
            currentHourWeatherData
        )
    }

    private fun getDetailsWeatherUiModel(
        currentWeather: CurrentWeather,
    ): DetailsWeatherUiModel {
        val detailsWeatherDataTitle = app.getString(R.string.title_details_weather_data)
        return currentWeather.toDetailsWeatherData(detailsWeatherDataTitle, dataUnit)
    }

    private fun getPrecipitationWeatherUiModel(
        currentWeather: CurrentWeather,
        forecast: Forecast,
    ): PrecipitationWeatherUiModel {
        val precipitationValue = when {
            currentWeather.rain != null -> currentWeather.rain.h.toInt()
            currentWeather.snow != null -> currentWeather.snow.h.toInt()
            else -> 0
        }
        val currentPrecipitationWeatherData = DisplayedWeatherItem(app.getString(R.string.text_time_now), precipitationValue)
        val precipitationWeatherDataTitle = app.getString(R.string.title_precipitation_weather_data)
        return forecast.toPrecipitationWeatherData(
            TIME_PATTERN_PRECIPITATION_WEATHER_DATA,
            SEGMENT_COUNT_PRECIPITATION_WEATHER_DATA,
            precipitationWeatherDataTitle,
            currentPrecipitationWeatherData
        )
    }

    private fun getForecastWeatherUiModel(
        forecast: Forecast,
    ): ForecastWeatherUiModel {
        val forecastDataTitle = app.getString(R.string.title_forecast_weather_data)
        return forecast.toForecastWeatherData(TIME_PATTERN_FORECAST_DATA, forecastDataTitle)
    }

    private suspend fun isFavoriteCity(cityId: String): Pair<Boolean, Boolean> {
        val isFavoriteCity = cityUseCase.isCityAdded(cityId)
            .getOrNull()
        val isOptionVisible = isFavoriteCity != null
        if (isOptionVisible.not()) return Pair(false, false)
        return Pair(requireNotNull(isFavoriteCity), true)
    }

    private fun displayError() {
        viewModelScope.launch {
            _weatherUiState.emit(
                WeatherUiState.Error(
                    titleResId = commonResR.string.text_common_error,
                )
            )
        }
    }

}
