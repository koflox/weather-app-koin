package com.koflox.weather.domain.entity

data class City(
    val id: String,
    val name: String,
    val country: String,
    val timezone: Int,
)
