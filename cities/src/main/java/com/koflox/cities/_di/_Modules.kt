package com.koflox.cities._di

import android.content.Context
import androidx.room.Room
import com.koflox.cities.cities.CitiesViewModel
import com.koflox.cities.data.source.CityLocalDataSource
import com.koflox.cities.data.source.CityRepository
import com.koflox.cities.data.source.CityRepositoryImpl
import com.koflox.cities.data.source.local.CityDatabase
import com.koflox.cities.data.source.local.CityLocalDataSourceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val uiModule = module {
    viewModel {
        CitiesViewModel(
            dataRepository = get(),
        )
    }
}

private val dataModule = module {
    factory { createCityDatabase(get()) }
    factory {
        get<CityDatabase>().favoriteCitiesDao
    }
    factory<CityLocalDataSource> {
        CityLocalDataSourceImpl(
            favoriteCitiesDao = get(),
            dispatcherIo = Dispatchers.IO,
        )
    }
    single<CityRepository> {
        CityRepositoryImpl(
            localDataSource = get(),
        )
    }
}

private fun createCityDatabase(context: Context): CityDatabase = Room.databaseBuilder(
    context.applicationContext,
    CityDatabase::class.java,
    "city_db"
).fallbackToDestructiveMigration().build()

val cityModules: List<Module> = listOf(
    uiModule,
    dataModule,
)
