package com.example.weather_app.data.response.open_weather_map.common


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)