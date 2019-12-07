package com.example.weather_app.data.response.open_weather_map.forecast


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
        @SerializedName("cod")
    val cod: String,
        @SerializedName("message")
    val message: Int,
        @SerializedName("cnt")
    val cnt: Int,
        @SerializedName("list")
        val list: List<WeatherData>,
        @SerializedName("city")
    val city: City
)