package com.koflox.cities.data.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favoriteCities"
)
data class FavoriteCity(
    @PrimaryKey
    val id: String,
    val cityName: String,
    val country: String,
    val timeAdded: Long,
)
