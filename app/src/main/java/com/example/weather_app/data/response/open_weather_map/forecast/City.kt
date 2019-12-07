package com.example.weather_app.data.response.open_weather_map.forecast


import com.example.weather_app.data.response.open_weather_map.common.Coord
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("population")
    val population: Int,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int
)