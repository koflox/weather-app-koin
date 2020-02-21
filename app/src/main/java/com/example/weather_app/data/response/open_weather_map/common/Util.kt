package com.example.weather_app.data.response.open_weather_map.common

import com.example.weather_app.BuildConfig
import com.example.weather_app.data.source.remote.OpenWeatherMapService

fun String.toOpenWeatherMapIconNameToUrl(extension: String = OpenWeatherMapService.ICON_EXTENSION): String {
    return "${BuildConfig.BASE_URL_OPEN_WEATHER_MAP_FOR_ICONS}$this.$extension"
}