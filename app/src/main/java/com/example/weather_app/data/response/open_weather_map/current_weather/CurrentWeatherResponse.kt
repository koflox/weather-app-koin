package com.example.weather_app.data.response.open_weather_map.current_weather


import com.example.weather_app.data.displayed.DetailsWeatherData
import com.example.weather_app.data.displayed.MainWeatherData
import com.example.weather_app.data.response.open_weather_map.common.*
import com.example.weather_app.data.response.open_weather_map.forecast.Rain
import com.example.weather_app.data.response.open_weather_map.forecast.Snow
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("snow")
    val snow: Snow?
)

fun CurrentWeatherResponse.toMainWeatherData() = MainWeatherData(
        //todo add unit sign
    temp = main.temp.toInt(),
    tempMin = main.tempMin.toInt(),
    tempMax = main.tempMax.toInt(),
        //todo add icon resources
        weatherIconRes = 0,
        weatherDescription = weather.firstOrNull()?.main ?: ""
)

fun CurrentWeatherResponse.toDetailsWeatherData() = DetailsWeatherData(
        //todo calculate it
    tempFeelsLike = "N/A",
        //todo add unit sign
        wind = wind.speed.toString(),
        humidity = main.humidity.toString(),
        pressure = main.pressure.toString(),
        //todo try to calculate or show N/A (now available)
    visibility = "N/A",
    dewPoint = "N/A"
)