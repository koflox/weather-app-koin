package com.example.weather_app.data.response.open_weather_map.current_weather


import com.example.weather_app.R
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

fun CurrentWeatherResponse.toDetailsWeatherData(): DetailsWeatherData {
    val values = mutableListOf<Pair<Int, String>>().apply {
        add(Pair(R.drawable.ic_thermometer, "Feels like\nN/A")) //todo calculate it
        add(Pair(R.drawable.ic_wind, "Wind\n${wind.speed.toInt()} m/s"))
        add(Pair(R.drawable.ic_humidity, "Humidity\n${main.humidity} %"))
        add(Pair(R.drawable.ic_barometer, "Pressure\n${main.pressure} hPa"))
        add(Pair(0, "Visibility\nN/A"))
        add(Pair(0, "Dew point\nN/A"))
    }
    return DetailsWeatherData(values)
}