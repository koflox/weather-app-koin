package com.koflox.weather.data.response.open_weather_map.common

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)
