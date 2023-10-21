package com.example.weather_app.data.source.remote

import androidx.lifecycle.LiveData
import com.example.weather_app.data.Result
import com.example.weather_app.data.data.FavoriteCity
import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.example.weather_app.data.response.open_weather_map.forecast.ForecastWeatherResponse
import com.example.weather_app.data.source.DataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("DeferredIsResult")
class RemoteDataSource(
    private val openWeatherMapService: OpenWeatherMapService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource {

    override suspend fun getCurrentWeather(query: String, units: String): Result<CurrentWeatherResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(openWeatherMapService.getCurrentWeather(query, units))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getCurrentWeather(cityId: Int, units: String): Result<CurrentWeatherResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(openWeatherMapService.getCurrentWeather(cityId, units))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getForecast(query: String, units: String): Result<ForecastWeatherResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(openWeatherMapService.getForecast(query, units))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun getForecast(cityId: Int, units: String): Result<ForecastWeatherResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                Result.Success(openWeatherMapService.getForecast(cityId, units))
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override fun observeFavoriteCities(): LiveData<Result<List<FavoriteCity>>> {
        TODO("no-op")
    }

    override suspend fun getFavoriteCities(): Result<List<FavoriteCity>> {
        TODO("no-op")
    }

    override suspend fun getFavoriteCity(cityId: Int): Result<FavoriteCity> {
        TODO("no-op")
    }

    override suspend fun insert(city: FavoriteCity) {
        TODO("no-op")
    }

    override suspend fun update(city: FavoriteCity) {
        TODO("no-op")
    }

    override suspend fun delete(city: FavoriteCity) {
        TODO("no-op")
    }

    override suspend fun deleteFavoriteCities() {
        TODO("no-op")
    }
}