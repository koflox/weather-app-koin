package com.koflox.weather.data.repo

import com.koflox.weather.data.source.remote.WeatherRemoteDataSource
import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.SystemOfMeasurement
import com.koflox.weather.domain.use_case.weather_info.WeatherQueryParam

internal class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        queryParam: WeatherQueryParam,
        unit: SystemOfMeasurement,
    ): CurrentWeather {
        return remoteDataSource.getCurrentWeather(queryParam, unit)
    }

    override suspend fun getForecast(
        queryParam: WeatherQueryParam,
        unit: SystemOfMeasurement,
    ): Forecast {
        return remoteDataSource.getForecast(queryParam, unit)
    }

}
