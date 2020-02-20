package com.example.weather_app.data.response.open_weather_map.current_weather


import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import com.example.weather_app.R
import com.example.weather_app.data.displayed.DetailsWeatherData
import com.example.weather_app.data.displayed.MainWeatherData
import com.example.weather_app.data.response.open_weather_map.common.*
import com.example.weather_app.data.response.open_weather_map.forecast.Rain
import com.example.weather_app.data.response.open_weather_map.forecast.Snow
import com.example.weather_app.util.formatToLocalTime
import com.google.gson.annotations.SerializedName
import java.util.*

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
    val cityName: String,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("rain")
    val rain: Rain?,
    @SerializedName("snow")
    val snow: Snow?
)

@SuppressLint("DefaultLocale")
fun CurrentWeatherResponse.toMainWeatherData(timePattern: String): MainWeatherData {
    val weather = weather.firstOrNull()
    return MainWeatherData(
        main.temp.toInt(),
        main.tempMin.toInt(),
        main.tempMax.toInt(),
        "°",
        "°C",
        weather?.icon,
        weather?.main ?: "",
        dt.formatToLocalTime(timePattern, timezone)
            .toLowerCase(Locale.getDefault())
            .capitalize()
    )
}

//todo place to right package
data class DetailsWeatherDataItem(
    @DrawableRes val resourceId: Int,
    val desc: String,
    val value: String,
    val unit: String = ""
)

fun CurrentWeatherResponse.toDetailsWeatherData(): DetailsWeatherData {
    val values = mutableListOf<DetailsWeatherDataItem>().apply {
        add(DetailsWeatherDataItem(R.drawable.ic_thermometer, "Feels like", "N/A")) //todo calculate it
        add(DetailsWeatherDataItem(R.drawable.ic_wind, "Wind", wind.speed.toInt().toString(), "m/s"))
        add(DetailsWeatherDataItem(R.drawable.ic_humidity, "Humidity", main.humidity.toString(), "%"))
        add(DetailsWeatherDataItem(R.drawable.ic_barometer, "Pressure", main.pressure.toString(), "hPa"))
        add(DetailsWeatherDataItem(0, "Visibility", "N/A"))
        add(DetailsWeatherDataItem(0, "Dew point", "N/A"))
    }
    return DetailsWeatherData(values)
}