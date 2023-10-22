package com.koflox.weather.data.response.open_weather_map.forecast

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String
)
