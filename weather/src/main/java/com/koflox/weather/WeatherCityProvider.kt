package com.koflox.weather

import com.koflox.weather.domain.entity.City

interface WeatherCityProvider {
    suspend fun isCityAdded(id: Int): Result<Boolean>
    suspend fun addCity(city: City): Result<Unit>
    suspend fun deleteCity(id: Int): Result<Unit>
}
