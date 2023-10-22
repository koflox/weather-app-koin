package com.koflox.weather.data.response.open_weather_map.forecast

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)
