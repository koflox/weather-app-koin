package com.example.weather_app.data.response.open_weather_map.common

import com.example.weather_app.R

/**
 * Converts OpenWeatherMap weather codes to specific icon resource ids
 */
//todo diversify codes, https://openweathermap.org/weather-conditions
fun Int.toWeatherIcon(): Int = when (this) {
    in 200..232 -> R.drawable.ic_thunderstorm
    in 300..321 -> R.drawable.ic_drizzle
    in 500..531 -> R.drawable.ic_rain
    in 600..622 -> R.drawable.ic_snow
    in 701..781 -> R.drawable.ic_fog
    800 -> R.drawable.ic_clear
    in 801..804 -> R.drawable.ic_clouds
    else -> R.drawable.ic_na
}