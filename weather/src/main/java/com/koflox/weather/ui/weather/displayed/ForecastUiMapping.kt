package com.koflox.weather.ui.weather.displayed

import com.koflox.common_jvm_util.formatToLocalTime
import com.koflox.weather.R
import com.koflox.weather.domain.entity.Forecast
import java.util.*
import kotlin.math.min

internal fun Forecast.toHourlyWeatherData(
    timePattern: String, desiredSegmentCount: Int,
    sectionTitle: String,
    vararg extra: DisplayedWeatherItem
): HourlyWeatherUiModel {
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
    return HourlyWeatherUiModel(sectionTitle, values)
}

internal fun Forecast.toPrecipitationWeatherData(
    timePattern: String, desiredSegmentCount: Int,
    sectionTitle: String,
    vararg extra: DisplayedWeatherItem
): PrecipitationWeatherUiModel {
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
    return PrecipitationWeatherUiModel(sectionTitle, values)
}

internal fun Forecast.toForecastWeatherData(timePattern: String, sectionTitle: String): ForecastWeatherUiModel {
    val values = mutableListOf<MainWeatherUiModel>().apply {
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
                } ?: run {
                    conditions[iconCode] = 1
                }
            }

            val isNextWeatherDataANewDay: Boolean = run {
                if (i + 1 >= list.size) return@run false
                val nextWeatherData = list[i + 1]
                val time = nextWeatherData.dateUtc
                time % 86400 == 0
            }
            if (isNextWeatherDataANewDay || i == list.indices.last) {
                val item = MainWeatherUiModel(
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
    return ForecastWeatherUiModel(sectionTitle, values)
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

