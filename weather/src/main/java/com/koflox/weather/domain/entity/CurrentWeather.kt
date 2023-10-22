package com.koflox.weather.domain.entity

data class CurrentWeather(
    val weather: List<WeatherDesc>,
    val main: MainParams,
    val wind: Wind,
    val dt: Int,
    val timezone: Int,
    val rain: Rain?,
    val snow: Snow?,
    val city: City,
)
