package com.koflox.weather.data.response.open_weather_map.forecast

import com.google.gson.annotations.SerializedName
import com.koflox.weather.data.response.open_weather_map.common.Clouds
import com.koflox.weather.data.response.open_weather_map.common.Main
import com.koflox.weather.data.response.open_weather_map.common.Weather
import com.koflox.weather.data.response.open_weather_map.common.Wind

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
