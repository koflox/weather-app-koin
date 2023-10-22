package com.koflox.weather.data.source

import com.koflox.common_jvm_util.Result
import com.koflox.weather.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.koflox.weather.data.response.open_weather_map.forecast.ForecastWeatherResponse

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(query: String, units: String): Result<CurrentWeatherResponse>
    suspend fun getCurrentWeather(cityId: Int, units: String): Result<CurrentWeatherResponse>
    suspend fun getForecast(query: String, units: String): Result<ForecastWeatherResponse>
    suspend fun getForecast(cityId: Int, units: String): Result<ForecastWeatherResponse>
}
