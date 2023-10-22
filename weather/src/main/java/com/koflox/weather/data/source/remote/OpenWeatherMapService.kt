package com.koflox.weather.data.source.remote

import com.koflox.weather.data.entity.remote.CurrentWeatherResponse
import com.koflox.weather.data.entity.remote.ForecastResponse
import com.koflox.weather.data.entity.remote.SystemOfMeasurementRemote
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenWeatherMapService {

    companion object {
        const val BASE_URL_DEFAULT = "https://openweathermap.org/"
        const val BASE_URL_API = "https://api.openweathermap.org/"
        const val PATH_IMAGES = "img/wn/"
        const val PATH_API_2_5 = "data/2.5/"
        const val ICON_EXTENSION_PNG = "png"
    }

    @GET("${PATH_API_2_5}weather")
    suspend fun getCurrentWeatherByPlaceName(
        @Query("q") place: String,
        @Query("units") unit: SystemOfMeasurementRemote,
    ): CurrentWeatherResponse

    @GET("${PATH_API_2_5}weather")
    suspend fun getCurrentWeatherByCityId(
        @Query("id") cityId: String,
        @Query("units") unit: SystemOfMeasurementRemote,
    ): CurrentWeatherResponse

    @GET("${PATH_API_2_5}forecast")
    suspend fun getForecastByPlaceName(
        @Query("q") place: String,
        @Query("units") unit: SystemOfMeasurementRemote,
    ): ForecastResponse

    @GET("${PATH_API_2_5}forecast")
    suspend fun getForecastByCityId(
        @Query("id") cityId: String,
        @Query("units") unit: SystemOfMeasurementRemote,
    ): ForecastResponse

}
