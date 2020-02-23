package com.example.weather_app.data.response.open_weather_map.common

import com.example.weather_app.BuildConfig
import com.example.weather_app.R
import com.example.weather_app.data.source.remote.OpenWeatherMapService


/**
 * Converts OpenWeatherMap weather icon codes into full url
 */
fun String.toOpenWeatherMapIconNameToUrl(extension: String = OpenWeatherMapService.ICON_EXTENSION): String {
    return "${BuildConfig.BASE_URL_OPEN_WEATHER_MAP_FOR_ICONS}$this.$extension"
}

/**
 * Converts OpenWeatherMap weather codes into specific icon resource ids
 */
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