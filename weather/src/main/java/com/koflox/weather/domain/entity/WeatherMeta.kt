package com.koflox.weather.domain.entity

data class MainParams(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMin: Double,
    val tempMax: Double,
)

data class WeatherDesc(
    val main: String,
    val weatherIconUrl: String?,
)

data class Wind(
    val speed: Double,
)

data class Rain(
    val h: Double,
)

data class Snow(
    val h: Double,
)
