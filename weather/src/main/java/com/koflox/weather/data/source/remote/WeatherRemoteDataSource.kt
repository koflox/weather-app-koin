package com.koflox.weather.data.source.remote

import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.SystemOfMeasurement
import com.koflox.weather.domain.use_case.weather_info.WeatherQueryParam

internal interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(
        queryParam: WeatherQueryParam,
        unit: SystemOfMeasurement,
    ): CurrentWeather

    suspend fun getForecast(
        queryParam: WeatherQueryParam,
        unit: SystemOfMeasurement,
    ): Forecast
}
