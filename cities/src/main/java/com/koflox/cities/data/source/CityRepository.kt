package com.koflox.cities.data.source

import androidx.lifecycle.LiveData
import com.koflox.cities.data.data.FavoriteCity
import com.koflox.common_jvm_util.Result

interface CityRepository {

    fun observeFavoriteCities(): LiveData<Result<List<FavoriteCity>>>

    suspend fun getFavoriteCities(): Result<List<FavoriteCity>>

    suspend fun getFavoriteCity(cityId: String): Result<FavoriteCity>

    suspend fun insert(city: FavoriteCity)

    suspend fun update(city: FavoriteCity)

    suspend fun delete(cityId: String)

    suspend fun deleteFavoriteCities()

    suspend fun isCityAdded(cityId: String): Boolean

}
