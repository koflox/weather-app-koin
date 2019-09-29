package com.example.weather_app.data.response.current_weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
        @SerializedName("current")
    val currentWeather: CurrentWeather,
        @SerializedName("location")
    val location: Location
)

