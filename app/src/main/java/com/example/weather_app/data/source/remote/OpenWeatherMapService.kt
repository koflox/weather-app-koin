package com.example.weather_app.data.source.remote

import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.example.weather_app.data.response.open_weather_map.forecast.ForecastWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Suppress("DeferredIsResult")
interface OpenWeatherMapService {

    companion object {
        const val ICON_EXTENSION = "png"
    }

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") query: String,
        @Query("units") units: String
    ): CurrentWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") cityId: Int,
        @Query("units") units: String
    ): CurrentWeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") query: String,
        @Query("units") units: String
    ): ForecastWeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("id") cityId: Int,
        @Query("units") units: String
    ): ForecastWeatherResponse

}