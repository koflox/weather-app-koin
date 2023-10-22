package com.koflox.weather.data.response.open_weather_map.forecast

import android.util.Log
import com.google.gson.annotations.SerializedName
import com.koflox.common_jvm_util.formatToLocalTime
import com.koflox.weather.data.response.open_weather_map.common.toOpenWeatherMapIconNameToUrl
import com.koflox.weather.displayed.DisplayedWeatherItem
import com.koflox.weather.displayed.ForecastWeatherData
import com.koflox.weather.displayed.HourlyWeatherData
import com.koflox.weather.displayed.MainWeatherData
import com.koflox.weather.displayed.PrecipitationWeatherData
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
    sectionTitle: String,
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
    return HourlyWeatherData(sectionTitle, values)
}

fun ForecastWeatherResponse.toPrecipitationWeatherData(
    timePattern: String, desiredSegmentCount: Int,
    sectionTitle: String,
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
    return PrecipitationWeatherData(sectionTitle, values)
}

fun ForecastWeatherResponse.toForecastWeatherData(timePattern: String, sectionTitle: String): ForecastWeatherData {
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
                        ?.toOpenWeatherMapIconNameToUrl(),
                    dayName = weatherData.dateUtc.formatToLocalTime(timePattern, city.timezone)
                        .lowercase(Locale.getDefault())
                        .capitalize(),
                    weatherDescription = ""
                )
                add(item)
                tempMin = Int.MAX_VALUE
                tempMax = Int.MIN_VALUE
                conditions.clear()
            }
        }
    }
    return ForecastWeatherData(sectionTitle, values)
}
