package com.example.weather_app.data.response.open_weather_map.forecast


import com.example.weather_app.data.response.open_weather_map.common.Clouds
import com.example.weather_app.data.response.open_weather_map.common.Main
import com.example.weather_app.data.response.open_weather_map.common.Weather
import com.example.weather_app.data.response.open_weather_map.common.Wind
import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("dt")
    val dateUtc: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("snow")
    val snow: Snow?
)