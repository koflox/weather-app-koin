package com.example.weather_app.data.source

import androidx.lifecycle.LiveData
import com.example.weather_app.data.Result
import com.example.weather_app.data.data.FavoriteCity
import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.example.weather_app.data.response.open_weather_map.forecast.ForecastWeatherResponse
import com.example.weather_app.data.source.remote.PixabayService

class AppDataRepository(
    private val remoteDataSource: DataSource,
    private val localDataSource: DataSource,
    private val pixabayService: PixabayService,
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

    override suspend fun getCurrentWeather(cityId: Int, units: String): Result<CurrentWeatherResponse> {
        return remoteDataSource.getCurrentWeather(cityId, units)
    }

    override suspend fun getForecast(
        query: String,
        units: String
    ): Result<ForecastWeatherResponse> {
        return remoteDataSource.getForecast(query, units)
    }

    override suspend fun getForecast(cityId: Int, units: String): Result<ForecastWeatherResponse> {
        return remoteDataSource.getForecast(cityId, units)
    }


    override suspend fun getFavoriteCities(): Result<List<FavoriteCity>> {
        return localDataSource.getFavoriteCities()
    }

    override fun observeFavoriteCities(): LiveData<Result<List<FavoriteCity>>> {
        return localDataSource.observeFavoriteCities()
    }

    override suspend fun getFavoriteCity(
        cityId: Int
    ): Result<FavoriteCity> {
        return localDataSource.getFavoriteCity(cityId)
    }

    override suspend fun insert(city: FavoriteCity) {
        city.run {
            try {
                val cityToSearch = "${cityName.replace(" ", "+")}+${country.replace(" ", "+")}"
                imageUrl = pixabayService.getPhotos(cityToSearch).hits.first().largeImageURL
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
        cityId: Int
    ): Boolean = localDataSource.getFavoriteCity(cityId) is Result.Success

}















