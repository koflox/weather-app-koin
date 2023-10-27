package com.example.weather_app

import com.koflox.cities.data.data.FavoriteCity
import com.koflox.cities.data.source.CityRepository
import com.koflox.weather.WeatherCityProvider
import com.koflox.weather.domain.entity.City

class WeatherCityProviderImpl(
    private val cityRepository: CityRepository,
) : WeatherCityProvider {
    override suspend fun isCityAdded(id: String): Result<Boolean> {
        return runCatching {
            cityRepository.isCityAdded(id)
        }
    }

    override suspend fun addCity(city: City): Result<Unit> {
        return runCatching {
            cityRepository.insert(
                FavoriteCity(
                    id = city.id,
                    cityName = city.name,
                    country = city.country,
                    timeAdded = System.currentTimeMillis(),
                )
            )
        }
    }

    override suspend fun deleteCity(id: String): Result<Unit> {
        return runCatching {
            cityRepository.delete(id)
        }
    }

}
