package com.example.weather_app.data

import com.example.weather_app.data.db.WeatherDatabase
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.data.network.OpenWeatherMapService
import com.example.weather_app.data.network.PixabayService
import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.example.weather_app.data.response.open_weather_map.forecast.ForecastWeatherResponse

class WeatherRepository(
    private val openWeatherMapService: OpenWeatherMapService,
    private val pixabayService: PixabayService,
    private val weatherDatabase: WeatherDatabase
) {

    init {
//        CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
//            try {
//                Log.d(
//                    "Logos",
//                    pixabayService.getPhotos("warsaw").await().hits.firstOrNull().toString()
//                )
//            } catch (e: Exception) {
//                Log.e("Logos", "error", e)
//            }
//        }
    }

    val favoriteCities by lazy { weatherDatabase.favoriteCitiesDao.getFavoriteCitiesAsync() }

    suspend fun getFavoriteCities() {

    }

    suspend fun getWeather(query: String): CurrentWeatherResponse {
        return openWeatherMapService.getCurrentWeather(query).await()
    }

    suspend fun getForecast(query: String): ForecastWeatherResponse {
        return openWeatherMapService.getForecast(query).await()
    }

    suspend fun addFavoriteCity(city: FavoriteCity) {
        city.run {
            try {
                val cityToSearch = "${cityName.replace(" ", "+")}+${country.replace(" ", "+")}"
                imageUrl = pixabayService.getPhotos(cityToSearch).await().hits.first().largeImageURL
            } catch (e: Exception) {
                e.printStackTrace()
            }
            weatherDatabase.favoriteCitiesDao.insert(this)
        }
    }

    suspend fun updateFavoriteCity(city: FavoriteCity) {
        weatherDatabase.favoriteCitiesDao.update(city)
    }

    suspend fun deleteFavoriteCity(city: FavoriteCity) {
        weatherDatabase.favoriteCitiesDao.delete(city)
    }

    suspend fun isCityAdded(cityName: String, latitude: Double, longitude: Double): Boolean {
        val favoriteCities = weatherDatabase.favoriteCitiesDao.getFavoriteCities()
        favoriteCities.firstOrNull {
            //            it.latitude == latitude && it.longitude == longitude
            cityName == it.cityName && latitude in it.latitude - 0.05..it.latitude + 0.05 && longitude in it.longitude - 0.05..it.longitude + 0.05
        }?.let {
            return true
        } ?: run {
            return false
        }
    }

}















