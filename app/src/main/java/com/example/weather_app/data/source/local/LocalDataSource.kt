package com.example.weather_app.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.weather_app.data.Result
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.example.weather_app.data.response.open_weather_map.forecast.ForecastWeatherResponse
import com.example.weather_app.data.source.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("DeferredIsResult", "IMPLICIT_NOTHING_AS_TYPE_PARAMETER")
class LocalDataSource(
    private val favoriteCitiesDao: FavoriteCitiesDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource {

    override suspend fun getCurrentWeather(query: String, units: String): Result<CurrentWeatherResponse> =
        withContext(ioDispatcher) {
            TODO("no-op")
        }

    override suspend fun getCurrentWeather(cityId: Int, units: String): Result<CurrentWeatherResponse> =
        withContext(ioDispatcher) {
            TODO("no-op")
        }

    override suspend fun getForecast(query: String, units: String): Result<ForecastWeatherResponse> =
        withContext(ioDispatcher) {
            TODO("no-op")
        }

    override suspend fun getForecast(cityId: Int, units: String): Result<ForecastWeatherResponse> =
        withContext(ioDispatcher) {
            TODO("no-op")
        }

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

    override suspend fun getFavoriteCity(cityId: Int): Result<FavoriteCity> = withContext(ioDispatcher) {
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

    override suspend fun delete(city: FavoriteCity) = withContext(ioDispatcher) {
        favoriteCitiesDao.delete(city)
    }

    override suspend fun deleteFavoriteCities() = withContext(ioDispatcher) {
        favoriteCitiesDao.deleteFavoriteCities()
    }

}