package com.example.weather_app.data.response.open_weather_map.forecast


import android.util.Log
import com.example.weather_app.data.displayed.ForecastWeatherData
import com.example.weather_app.data.displayed.HourlyWeatherData
import com.example.weather_app.data.displayed.MainWeatherData
import com.example.weather_app.data.displayed.PrecipitationWeatherData
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

fun ForecastWeatherResponse.toHourlyWeatherData(timePattern: String, desiredSegmentCount: Int,
                                                vararg extra: Pair<String, Int>): HourlyWeatherData {
    val segmentCount = min(desiredSegmentCount, list.size)
    val values = mutableListOf<Pair<String, Int>>().apply {
        extra.forEach {
            add(it)
        }
        for (i in 0..segmentCount) {
            val weatherData = list[i]
            val time = weatherData.dateUtc
                    .formatToLocalTime(timePattern, city.timezone)
                    .toLowerCase(Locale.getDefault())
            val temperature = weatherData.main.temp.toInt()
            add(Pair(time, temperature))
        }
    }
    return HourlyWeatherData(values)
}

fun ForecastWeatherResponse.toPrecipitationWeatherData(timePattern: String, desiredSegmentCount: Int,
                                                       vararg extra: Pair<String, Int>): PrecipitationWeatherData {
    val segmentCount = min(desiredSegmentCount, list.size)
    val values = mutableListOf<Pair<String, Int>>().apply {
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
            add(Pair(time, precipitationValue))
        }
    }
    return PrecipitationWeatherData(values)
}

//todo check this method
fun ForecastWeatherResponse.toForecastWeatherData(): ForecastWeatherData {
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
                add(MainWeatherData(
                        temp = (tempMax + tempMin) / 2,
                        tempMax = tempMax,
                        tempMin = tempMin,
                        weatherIconRes = 0,
//                        weatherIconRes = conditions.maxBy { it.value }?.key?.toInt() ?: 0, //todo
                        dayName = "", //todo
                        weatherDescription = ""
                ))
                tempMin = Int.MAX_VALUE
                tempMax = Int.MIN_VALUE
                conditions.clear()
            }
        }
    }
    Log.d("Logos", "forecast size: ${values.size}, values = $values")
    return ForecastWeatherData(values)
}



















