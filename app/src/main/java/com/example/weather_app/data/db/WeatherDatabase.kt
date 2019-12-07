package com.example.weather_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather_app.data.entity.FavoriteCity

@Database(entities = [FavoriteCity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val favoriteCitiesDao: FavoriteCitiesDao

}