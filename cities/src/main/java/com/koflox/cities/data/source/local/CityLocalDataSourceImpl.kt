package com.koflox.cities.data.source.local

import com.koflox.cities.data.data.FavoriteCity
import com.koflox.cities.data.source.CityLocalDataSource
import com.koflox.common_jvm_util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CityLocalDataSourceImpl(
    private val favoriteCitiesDao: CityDao,
    private val dispatcherIo: CoroutineDispatcher,
) : CityLocalDataSource {

    override fun observeFavoriteCities(): Flow<List<FavoriteCity>> {
        return favoriteCitiesDao.observeCities()
            .flowOn(dispatcherIo)
    }

    override suspend fun getFavoriteCities(): Result<List<FavoriteCity>> =
        withContext(dispatcherIo) {
            return@withContext try {
                Result.Success(favoriteCitiesDao.getFavoriteCities())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getFavoriteCity(cityId: String): Result<FavoriteCity> = withContext(dispatcherIo) {
        val city = favoriteCitiesDao.getFavoriteCity(cityId)
        return@withContext when {
            city != null -> Result.Success(city)
            else -> Result.Error(NullPointerException())
        }
    }

    override suspend fun insert(city: FavoriteCity) = withContext(dispatcherIo) {
        favoriteCitiesDao.insert(city)
    }

    override suspend fun update(city: FavoriteCity) = withContext(dispatcherIo) {
        favoriteCitiesDao.update(city)
    }

    override suspend fun delete(cityId: String) = withContext(dispatcherIo) {
        favoriteCitiesDao.delete(cityId)
    }

    override suspend fun deleteFavoriteCities() = withContext(dispatcherIo) {
        favoriteCitiesDao.deleteFavoriteCities()
    }

}