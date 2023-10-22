package com.koflox.weather.data.source

import com.koflox.common_jvm_util.Result
import com.koflox.weather.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.koflox.weather.data.response.open_weather_map.forecast.ForecastWeatherResponse

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        query: String,
        units: String
    ): Result<CurrentWeatherResponse> {
        return remoteDataSource.getCurrentWeather(query, units)
    }

    override suspend fun getCurrentWeather(cityId: Int, units: String): Result<CurrentWeatherResponse> {
        return remoteDataSource.getCurrentWeather(cityId, units)
    }

    override suspend fun getForecast(
        query: String,
        units: String
    ): Result<ForecastWeatherResponse> {
        return remoteDataSource.getForecast(query, units)
    }

    override suspend fun getForecast(cityId: Int, units: String): Result<ForecastWeatherResponse> {
        return remoteDataSource.getForecast(cityId, units)
    }

}
