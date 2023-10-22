package com.koflox.cities.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favoriteCities"
)
data class FavoriteCity(
    @PrimaryKey
    val id: Int,
    val cityName: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timeAdded: Long,
)
