package com.koflox.weather.data.response.open_weather_map.common

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)
