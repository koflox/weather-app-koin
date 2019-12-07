package com.example.weather_app.data.response.open_weather_map.common


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int
)