package com.example.weather_app.data.network

import com.example.weather_app.data.response.current_weather.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

@Suppress("DeferredIsResult")
interface ApixuService {

    /**
    * Fetches current weatherInfo
    * @param query - US Zipcode, UK Postcode, Canada Postalcode, IP address,
    * Latitude/Longitude (decimal degree) or city name
    */
    @GET("current.json") //todo add language to params
    fun getWeather(@Query("q") query: String): Deferred<CurrentWeatherResponse>

}