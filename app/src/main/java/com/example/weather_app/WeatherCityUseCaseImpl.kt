package com.example.weather_app

import com.koflox.cities.data.data.FavoriteCity
import com.koflox.cities.data.source.CityRepository
import com.koflox.weather.WeatherCityUseCase
import com.koflox.weather.data.response.open_weather_map.current_weather.WeatherCity

// todo propagate error and runtime exceptions from runCatching
class WeatherCityUseCaseImpl(
    private val cityRepository: CityRepository,
) : WeatherCityUseCase {
    override suspend fun isCityAdded(id: Int): Result<Boolean> {
        return runCatching {
            cityRepository.isCityAdded(id)
        }
    }

    override suspend fun addCity(city: WeatherCity): Result<Unit> {
        return runCatching {
            cityRepository.insert(
                FavoriteCity(
                    id = city.id,
                    cityName = city.cityName,
                    country = city.country,
                    latitude = city.latitude,
                    longitude = city.longitude,
                    timeAdded = System.currentTimeMillis(),
                )
            )
        }
    }

    override suspend fun deleteCity(id: Int): Result<Unit> {
        return runCatching {
            cityRepository.delete(id)
        }
    }
}
