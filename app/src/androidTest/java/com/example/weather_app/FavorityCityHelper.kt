package com.example.weather_app

import com.example.weather_app.data.data.FavoriteCity

fun generateFavoriteCities(amount: Int) = Array(amount) { index ->
    FavoriteCity(
        index,
        "cityName$index",
        "country$index",
        index.toDouble(),
        index.toDouble(),
        System.currentTimeMillis()
    )
}

fun generateFavoriteCityWithId(id: Int, cityName: String = "cityName") = FavoriteCity(
    id,
    cityName,
    "country",
    id.toDouble(),
    id.toDouble(),
    System.currentTimeMillis()
)