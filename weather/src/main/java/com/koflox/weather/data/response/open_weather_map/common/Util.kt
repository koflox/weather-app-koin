package com.koflox.weather.data.response.open_weather_map.common

import com.koflox.weather.R
import com.koflox.weather.data.source.remote.OpenWeatherMapService
import com.koflox.common_android_res.R as commonResR

fun String.toOpenWeatherMapIconNameToUrl(extension: String = OpenWeatherMapService.ICON_EXTENSION): String {
    return "https://openweathermap.org/img/wn/$this.$extension"
}

fun Int.toWeatherIcon(): Int = when (this) {
    in 200..232 -> R.drawable.ic_thunderstorm
    in 300..321 -> R.drawable.ic_drizzle
    in 500..531 -> R.drawable.ic_rain
    in 600..622 -> R.drawable.ic_snow
    in 701..781 -> R.drawable.ic_fog
    800 -> R.drawable.ic_clear
    in 801..804 -> R.drawable.ic_clouds
    else -> commonResR.drawable.ic_na
}
