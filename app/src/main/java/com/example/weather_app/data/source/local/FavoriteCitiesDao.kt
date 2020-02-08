package com.example.weather_app.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather_app.data.entity.FavoriteCity

@Dao
interface FavoriteCitiesDao {

    @Query("SELECT * FROM favoriteCities")
    fun observeFavoriteCities(): LiveData<List<FavoriteCity>>

    @Query("SELECT * FROM favoriteCities")
    suspend fun getFavoriteCities(): List<FavoriteCity>

    @Query("SELECT * FROM favoriteCities WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun getFavoriteCity(latitude: Double, longitude: Double): FavoriteCity?

    @Insert
    suspend fun insert(city: FavoriteCity)

    @Update
    suspend fun update(city: FavoriteCity)

    @Delete
    suspend fun delete(city: FavoriteCity)

    @Query("DELETE FROM favoriteCities")
    suspend fun deleteFavoriteCities()

}