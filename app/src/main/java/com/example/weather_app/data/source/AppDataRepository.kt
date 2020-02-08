package com.example.weather_app.data.source

import androidx.lifecycle.LiveData
import com.example.weather_app.data.Result
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.example.weather_app.data.response.open_weather_map.forecast.ForecastWeatherResponse
import com.example.weather_app.data.source.remote.PixabayService

class AppDataRepository(
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource,
    private val pixabayService: PixabayService
) : DataRepository {

    val favoriteCities: LiveData<Result<List<FavoriteCity>>> by lazy(LazyThreadSafetyMode.NONE) {
        localDataSource.observeFavoriteCities()
    }

    override suspend fun getCurrentWeather(
        query: String,
        units: String
    ): Result<CurrentWeatherResponse> {
        return remoteDataSource.getCurrentWeather(query, units)
    }

    override suspend fun getForecast(
        query: String,
        units: String
    ): Result<ForecastWeatherResponse> {
        return remoteDataSource.getForecast(query, units)
    }

    override suspend fun getFavoriteCities(): Result<List<FavoriteCity>> {
        return localDataSource.getFavoriteCities()
    }

    override fun observeFavoriteCities(): LiveData<Result<List<FavoriteCity>>> {
        return localDataSource.observeFavoriteCities()
    }

    override suspend fun getFavoriteCity(
        latitude: Double,
        longitude: Double
    ): Result<FavoriteCity> {
        return localDataSource.getFavoriteCity(latitude, longitude)
    }

    //todo refactor FavoriteCity object to use integer/long id as primary key
    override suspend fun insert(city: FavoriteCity) {
        city.run {
            try {
                val cityToSearch = "${cityName.replace(" ", "+")}+${country.replace(" ", "+")}"
                imageUrl = pixabayService.getPhotos(cityToSearch).await().hits.first().largeImageURL
            } catch (e: Exception) {
                e.printStackTrace()
            }
            localDataSource.insert(this)
        }
    }

    override suspend fun update(city: FavoriteCity) {
        localDataSource.update(city)
    }

    override suspend fun delete(city: FavoriteCity) {
        localDataSource.delete(city)
    }

    override suspend fun deleteFavoriteCities() {
        localDataSource.deleteFavoriteCities()
    }

    override suspend fun isCityAdded(
        cityName: String,
        latitude: Double,
        longitude: Double
    ): Boolean {
        val favoriteCities = localDataSource.getFavoriteCities() as? Result.Success ?: return false
        return favoriteCities.data.find {
            cityName == it.cityName
                    && latitude in it.latitude - 0.05..it.latitude + 0.05
                    && longitude in it.longitude - 0.05..it.longitude + 0.05
        } != null
    }

}















