package com.koflox.cities.data.source

import com.koflox.cities.data.data.FavoriteCity
import com.koflox.common_jvm_util.Result
import kotlinx.coroutines.flow.Flow

class CityRepositoryImpl(
    private val localDataSource: CityLocalDataSource,
) : CityRepository {

    override suspend fun getFavoriteCities(): Result<List<FavoriteCity>> {
        return localDataSource.getFavoriteCities()
    }

    override fun observeCities(): Flow<List<FavoriteCity>> {
        return localDataSource.observeFavoriteCities()
    }

    override suspend fun getFavoriteCity(
        cityId: String,
    ): Result<FavoriteCity> {
        return localDataSource.getFavoriteCity(cityId)
    }

    override suspend fun insert(city: FavoriteCity) {
        localDataSource.insert(city)
    }

    override suspend fun update(city: FavoriteCity) {
        localDataSource.update(city)
    }

    override suspend fun delete(cityId: String) {
        localDataSource.delete(cityId)
    }

    override suspend fun deleteFavoriteCities() {
        localDataSource.deleteFavoriteCities()
    }

    override suspend fun isCityAdded(
        cityId: String,
    ): Boolean = localDataSource.getFavoriteCity(cityId) is Result.Success

}
