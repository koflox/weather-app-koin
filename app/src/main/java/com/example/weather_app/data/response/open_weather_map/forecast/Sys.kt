package com.example.weather_app.data.response.open_weather_map.forecast


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String
)