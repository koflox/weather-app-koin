package com.koflox.weather

import com.koflox.weather.data.response.open_weather_map.current_weather.WeatherCity

interface WeatherCityUseCase {
    suspend fun isCityAdded(id: Int): Result<Boolean>
    suspend fun addCity(city: WeatherCity): Result<Unit>
    suspend fun deleteCity(id: Int): Result<Unit>
}
