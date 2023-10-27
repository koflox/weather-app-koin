package com.koflox.cities.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.koflox.cities.data.data.FavoriteCity
import com.koflox.cities.data.source.CityLocalDataSource
import com.koflox.common_jvm_util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class CityLocalDataSourceImpl(
    private val favoriteCitiesDao: CityDao,
    private val ioDispatcher: CoroutineDispatcher,
) : CityLocalDataSource {

    override fun observeFavoriteCities(): LiveData<Result<List<FavoriteCity>>> {
        return favoriteCitiesDao.observeFavoriteCities().map {
            Result.Success(it)
        }
    }

    override suspend fun getFavoriteCities(): Result<List<FavoriteCity>> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(favoriteCitiesDao.getFavoriteCities())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getFavoriteCity(cityId: String): Result<FavoriteCity> = withContext(ioDispatcher) {
        val city = favoriteCitiesDao.getFavoriteCity(cityId)
        return@withContext when {
            city != null -> Result.Success(city)
            else -> Result.Error(NullPointerException())
        }
    }

    override suspend fun insert(city: FavoriteCity) = withContext(ioDispatcher) {
        favoriteCitiesDao.insert(city)
    }

    override suspend fun update(city: FavoriteCity) = withContext(ioDispatcher) {
        favoriteCitiesDao.update(city)
    }

    override suspend fun delete(cityId: String) = withContext(ioDispatcher) {
        favoriteCitiesDao.delete(cityId)
    }

    override suspend fun deleteFavoriteCities() = withContext(ioDispatcher) {
        favoriteCitiesDao.deleteFavoriteCities()
    }

}