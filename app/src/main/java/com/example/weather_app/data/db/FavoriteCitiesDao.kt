package com.example.weather_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_app.data.entity.FavoriteCity

@Dao
interface FavoriteCitiesDao {

    @Query("SELECT * FROM favoriteCities")
    fun getFavoriteCitiesAsync(): LiveData<List<FavoriteCity>>

    @Query("SELECT * FROM favoriteCities")
    fun getFavoriteCities(): List<FavoriteCity>

    @Query("SELECT * FROM favoriteCities WHERE latitude = :latitude AND longitude = :longitude")
    fun getFavoriteCity(latitude: Double, longitude: Double): FavoriteCity?

    @Insert
    fun insert(city: FavoriteCity)

    @Update
    fun update(city: FavoriteCity)

    @Delete
    fun delete(city: FavoriteCity)

}