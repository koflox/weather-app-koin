package com.koflox.cities.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.koflox.cities.data.data.FavoriteCity

@Database(
    entities = [
        FavoriteCity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class CityDatabase : RoomDatabase() {

    abstract val favoriteCitiesDao: CityDao

}
