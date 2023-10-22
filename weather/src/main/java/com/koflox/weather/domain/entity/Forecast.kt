package com.koflox.weather.domain.entity

data class Forecast(
    val list: List<DayWeather>,
    val city: City,
)
