package com.koflox.weather.ui.displayed

import android.util.Log
import com.koflox.common_jvm_util.formatToLocalTime
import com.koflox.weather.R
import com.koflox.weather.domain.entity.Forecast
import java.util.*
import kotlin.math.min

internal fun Forecast.toHourlyWeatherData(
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
                .lowercase(Locale.getDefault())
            val temperature = weatherData.main.temp.toInt()
            add(DisplayedWeatherItem(time, temperature))
        }
    }
    return HourlyWeatherData(sectionTitle, values)
}

internal fun Forecast.toPrecipitationWeatherData(
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
                .lowercase(Locale.getDefault())
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

internal fun Forecast.toForecastWeatherData(timePattern: String, sectionTitle: String): ForecastWeatherData {
    val values = mutableListOf<MainWeatherData>().apply {
        var tempMin = Int.MAX_VALUE
        var tempMax = Int.MIN_VALUE
        val conditions = mutableMapOf<String, Int>()
        for (i in list.indices) {
            val weatherData = list[i]
            if (weatherData.main.temp < tempMin) tempMin = weatherData.main.temp.toInt()
            if (weatherData.main.temp > tempMax) tempMax = weatherData.main.temp.toInt()
            weatherData.weatherDescriptions.firstOrNull()?.weatherIconUrl?.let { iconCode ->
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
                    weatherIconUrl = conditions.maxBy { it.value }.key,
                    dayName = weatherData.dateUtc.formatToLocalTime(timePattern, city.timezone)
                        .lowercase(Locale.getDefault()),
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

internal fun Int.toWeatherIcon(): Int = when (this) {
    in 200..232 -> R.drawable.ic_thunderstorm
    in 300..321 -> R.drawable.ic_drizzle
    in 500..531 -> R.drawable.ic_rain
    in 600..622 -> R.drawable.ic_snow
    in 701..781 -> R.drawable.ic_fog
    800 -> R.drawable.ic_clear
    in 801..804 -> R.drawable.ic_clouds
    else -> com.koflox.common_android_res.R.drawable.ic_na
}

