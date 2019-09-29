package com.example.weather_app.data.entity

import androidx.room.Entity

@Entity(
    tableName = "favoriteCities",
    primaryKeys = ["latitude", "longitude"]
)
data class FavoriteCity(
    val cityName: String,
    val region: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timeAdded: Long,
    var imageUrl: String = ""
)