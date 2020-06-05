package com.example.weather_app

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weather_app.data.source.local.FavoriteCitiesDao
import com.example.weather_app.data.source.local.WeatherDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteCitiesDaoTest {

    private lateinit var weatherDatabase: WeatherDatabase
    private lateinit var favoriteCitiesDao: FavoriteCitiesDao

    @Before
    fun setup() {
        weatherDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            WeatherDatabase::class.java
        ).build()
        favoriteCitiesDao = weatherDatabase.favoriteCitiesDao
    }

    @After
    fun teardown() {
        weatherDatabase.close()
    }

    @Test
    fun insertAndReadTheSameFavoriteCity() = runBlocking {
        val city = generateFavoriteCities(1)[0]

        favoriteCitiesDao.insert(city)
        val dbCities = favoriteCitiesDao.getFavoriteCities()

        assertEquals(1, dbCities.size)
        assertTrue(city == dbCities[0])
    }

    @Test
    fun updateAndReadTheSameFavoriteCity() = runBlocking {
        val city = generateFavoriteCities(1)[0]

        favoriteCitiesDao.insert(city)
        city.imageUrl = "newImageUrl"
        favoriteCitiesDao.update(city)

        val dbCities = favoriteCitiesDao.getFavoriteCities()
        assertTrue(city == dbCities[0])
    }

    @Test
    fun insertAndReadAllFavoriteCities() = runBlocking {
        val citiesToGenerate = 10
        val cities = generateFavoriteCities(citiesToGenerate)
        cities.forEach { city ->
            favoriteCitiesDao.insert(city)
        }
        assertEquals(citiesToGenerate, favoriteCitiesDao.getFavoriteCities().size)
    }

    @Test
    fun deleteAllCitiesAndReadNothing() = runBlocking {
        val city = generateFavoriteCities(1)[0]
        favoriteCitiesDao.insert(city)
        favoriteCitiesDao.deleteFavoriteCities()
        assertTrue(favoriteCitiesDao.getFavoriteCities().isEmpty())
    }

    @Test
    fun cityWithTheSameIdIsReplaced() = runBlocking {
        val cityId = 0
        val originalCityName = "cityOriginal"
        val duplicatedCityName = "duplicatedCityName"
        val originalCity = generateFavoriteCityWithId(cityId, originalCityName)
        val duplicatedCity = generateFavoriteCityWithId(cityId, duplicatedCityName)

        favoriteCitiesDao.insert(originalCity)
        favoriteCitiesDao.insert(duplicatedCity)

        assertEquals(1, favoriteCitiesDao.getFavoriteCities().size)
        assertTrue(duplicatedCityName == favoriteCitiesDao.getFavoriteCities()[0].cityName)
    }

}