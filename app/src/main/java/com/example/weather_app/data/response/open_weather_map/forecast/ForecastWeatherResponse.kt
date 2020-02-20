package com.example.weather_app.data.response.open_weather_map.forecast


import android.annotation.SuppressLint
import android.util.Log
import com.example.weather_app.data.displayed.*
import com.example.weather_app.data.response.open_weather_map.common.owmIconNameToUrl
import com.example.weather_app.data.source.remote.OpenWeatherMapService
import com.example.weather_app.util.formatToLocalTime
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.math.min

data class ForecastWeatherResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("list")
    val list: List<WeatherData>,
    @SerializedName("city")
    val city: City
)

fun ForecastWeatherResponse.toHourlyWeatherData(
    timePattern: String, desiredSegmentCount: Int,
    vararg extra: DisplayedWeatherItem
): HourlyWeatherData {
    val segmentCount = min(desiredSegmentCount, list.size)
    val values = mutableListOf<DisplayedWeatherItem>().apply {
        extra.forEach {
            add(it)
        }
        for (i in 0..segmentCount) {
            val weatherData = list[i]
            val time = weatherData.dateUtc
                .formatToLocalTime(timePattern, city.timezone)
                .toLowerCase(Locale.getDefault())
            val temperature = weatherData.main.temp.toInt()
            add(DisplayedWeatherItem(time, temperature))
        }
    }
    return HourlyWeatherData(values)
}

fun ForecastWeatherResponse.toPrecipitationWeatherData(
    timePattern: String, desiredSegmentCount: Int,
    vararg extra: DisplayedWeatherItem
): PrecipitationWeatherData {
    val segmentCount = min(desiredSegmentCount, list.size)
    val values = mutableListOf<DisplayedWeatherItem>().apply {
        extra.forEach {
            add(it)
        }
        for (i in 0..segmentCount) {
            val weatherData = list[i]
            val time = weatherData.dateUtc
                .formatToLocalTime(timePattern, city.timezone)
                .toLowerCase(Locale.getDefault())
            val precipitationValue = when {
                weatherData.rain != null -> weatherData.rain.h.toInt()
                weatherData.snow != null -> weatherData.snow.h.toInt()
                else -> 0
            }
            add(DisplayedWeatherItem(time, precipitationValue))
        }
    }
    return PrecipitationWeatherData(values)
}

//todo needs refactoring
@SuppressLint("DefaultLocale")
fun ForecastWeatherResponse.toForecastWeatherData(timePattern: String): ForecastWeatherData {
    val values = mutableListOf<MainWeatherData>().apply {
        var tempMin = Int.MAX_VALUE
        var tempMax = Int.MIN_VALUE
        val conditions = mutableMapOf<String, Int>()
        for (i in list.indices) {
            val weatherData = list[i]
            if (weatherData.main.temp < tempMin) tempMin = weatherData.main.temp.toInt()
            if (weatherData.main.temp > tempMax) tempMax = weatherData.main.temp.toInt()
            weatherData.weather.firstOrNull()?.icon?.let { iconCode ->
                conditions[iconCode]?.run {
                    conditions[iconCode] = this + 1
                } ?: kotlin.run {
                    conditions[iconCode] = 1
                }
            }

            val nextWeatherDataIsNewDay = try {
                val nextWeatherData = list[i + 1]
                val time = nextWeatherData.dateUtc
                time % 86400 == 0
            } catch (e: Exception) {
                Log.d("Logos", "e: Exception")
                false
            }
            if (nextWeatherDataIsNewDay || i == list.indices.last) {
                val item = MainWeatherData(
                    temp = (tempMax + tempMin) / 2,
                    tempMax = tempMax,
                    tempMin = tempMin,
                    tempUnitMain = "°C",
                    weatherIconUrl = conditions.maxBy { it.value }?.key
                        ?.owmIconNameToUrl(OpenWeatherMapService.ICON_EXTENSION),
                    dayName = weatherData.dateUtc.formatToLocalTime(timePattern, city.timezone)
                        .toLowerCase(Locale.getDefault())
                        .capitalize(),
                    weatherDescription = ""
                )
                add(item)
                Log.d("Logoss", "${item.weatherIconUrl}")
                tempMin = Int.MAX_VALUE
                tempMax = Int.MIN_VALUE
                conditions.clear()
            }
        }
    }
    Log.d("Logos", "forecast size: ${values.size}, values = $values")
    return ForecastWeatherData(values)
}