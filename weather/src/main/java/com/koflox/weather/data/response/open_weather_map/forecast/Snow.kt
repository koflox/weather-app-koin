package com.koflox.weather.data.response.open_weather_map.forecast

import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("3h")
    val h: Double
)
