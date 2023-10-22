package com.koflox.weather.data.response.open_weather_map.current_weather

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import com.koflox.common_jvm_util.formatToLocalTime
import com.koflox.weather.R
import com.koflox.weather.data.data.Unit
import com.koflox.weather.data.response.open_weather_map.common.Clouds
import com.koflox.weather.data.response.open_weather_map.common.Coord
import com.koflox.weather.data.response.open_weather_map.common.Main
import com.koflox.weather.data.response.open_weather_map.common.Weather
import com.koflox.weather.data.response.open_weather_map.common.Wind
import com.koflox.weather.data.response.open_weather_map.common.toOpenWeatherMapIconNameToUrl
import com.koflox.weather.data.response.open_weather_map.forecast.Rain
import com.koflox.weather.data.response.open_weather_map.forecast.Snow
import com.koflox.weather.displayed.DetailsWeatherData
import com.koflox.weather.displayed.DetailsWeatherDataItem
import com.koflox.weather.displayed.MainWeatherData
import java.util.*
import com.koflox.common_android_res.R as commonResR

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

class WeatherCity(
    val id: Int,
    val cityName: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
)

fun CurrentWeatherResponse.toCity() = WeatherCity(
    id,
    cityName,
    sys.country,
    coord.lat,
    coord.lon,
)

@SuppressLint("DefaultLocale")
fun CurrentWeatherResponse.toMainWeatherData(timePattern: String, unit: Unit): MainWeatherData {
    val weather = weather.firstOrNull()
    val tempUnitMain = when (unit) {
        Unit.METRIC -> "°C"
        Unit.IMPERIAL -> "F"
    }
    val tempUnitExtra = when (unit) {
        Unit.METRIC -> "°"
        Unit.IMPERIAL -> "F"
    }
    return MainWeatherData(
        main.temp.toInt(),
        main.tempMin.toInt(),
        main.tempMax.toInt(),
        tempUnitMain,
        tempUnitExtra,
        weather?.icon?.toOpenWeatherMapIconNameToUrl(),
        weather?.main ?: "",
        dt.formatToLocalTime(timePattern, timezone)
            .lowercase(Locale.getDefault())
            .capitalize()
    )
}

//todo calculate "feels like", "visibility" and "dew point" values
fun CurrentWeatherResponse.toDetailsWeatherData(sectionTitle: String, unit: Unit): DetailsWeatherData {
    val windSpeedUnit = when (unit) {
        Unit.METRIC -> "m/s"
        Unit.IMPERIAL -> "m/h"
    }
    val values = mutableListOf<DetailsWeatherDataItem>().apply {
        add(DetailsWeatherDataItem(R.drawable.ic_thermometer, "Feels like", "N/A"))
        add(DetailsWeatherDataItem(R.drawable.ic_wind, "Wind", wind.speed.toInt().toString(), windSpeedUnit))
        add(DetailsWeatherDataItem(R.drawable.ic_humidity, "Humidity", main.humidity.toString(), "%"))
        add(DetailsWeatherDataItem(R.drawable.ic_barometer, "Pressure", main.pressure.toString(), "hPa"))
        add(DetailsWeatherDataItem(commonResR.drawable.ic_na, "Visibility", "N/A"))
        add(DetailsWeatherDataItem(commonResR.drawable.ic_na, "Dew point", "N/A"))
    }
    return DetailsWeatherData(sectionTitle, values)
}
