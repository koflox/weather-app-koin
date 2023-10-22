package com.koflox.weather.domain.use_case.weather_info

import com.koflox.weather.data.repo.WeatherRepository
import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.SystemOfMeasurement

interface WeatherInfoUseCase {
    suspend fun getCurrentWeather(queryParam: WeatherQueryParam, unit: SystemOfMeasurement): Result<CurrentWeather>
    suspend fun getForecast(queryParam: WeatherQueryParam, unit: SystemOfMeasurement): Result<Forecast>
}

internal class WeatherInfoUseCaseImpl(
    private val repo: WeatherRepository,
) : WeatherInfoUseCase {
    override suspend fun getCurrentWeather(queryParam: WeatherQueryParam, unit: SystemOfMeasurement): Result<CurrentWeather> {
        return runCatching {
            repo.getCurrentWeather(queryParam, unit)
        }
    }

    override suspend fun getForecast(queryParam: WeatherQueryParam, unit: SystemOfMeasurement): Result<Forecast> {
        return runCatching {
            repo.getForecast(queryParam, unit)
        }
    }

}
