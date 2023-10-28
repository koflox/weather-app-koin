package com.koflox.cities.data.source

import com.koflox.cities.data.data.FavoriteCity
import com.koflox.common_jvm_util.Result
import kotlinx.coroutines.flow.Flow

interface CityLocalDataSource {

    fun observeFavoriteCities(): Flow<List<FavoriteCity>>

    suspend fun getFavoriteCities(): Result<List<FavoriteCity>>

    suspend fun getFavoriteCity(cityId: String): Result<FavoriteCity>

    suspend fun insert(city: FavoriteCity)

    suspend fun update(city: FavoriteCity)

    suspend fun delete(cityId: String)

    suspend fun deleteFavoriteCities()

}
