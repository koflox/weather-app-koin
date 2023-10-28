package com.koflox.cities.data.source.local

import androidx.room.*
import com.koflox.cities.data.data.FavoriteCity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM favoriteCities")
    fun observeCities(): Flow<List<FavoriteCity>>

    @Query("SELECT * FROM favoriteCities")
    suspend fun getFavoriteCities(): List<FavoriteCity>

    @Query("SELECT * FROM favoriteCities WHERE id = :cityId")
    suspend fun getFavoriteCity(cityId: String): FavoriteCity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: FavoriteCity)

    @Update
    suspend fun update(city: FavoriteCity)

    @Query("DELETE FROM favoriteCities where id = :cityId")
    suspend fun delete(cityId: String)

    @Query("DELETE FROM favoriteCities")
    suspend fun deleteFavoriteCities()

}