package com.koflox.weather.domain.entity

data class DayWeather(
    val dateUtc: Int,
    val main: MainParams,
    val weatherDescriptions: List<WeatherDesc>,
    val rain: Rain?,
    val snow: Snow?,
)