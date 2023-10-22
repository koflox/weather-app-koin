package com.koflox.weather._di

import com.koflox.weather.data.repo.WeatherRepository
import com.koflox.weather.data.repo.WeatherRepositoryImpl
import com.koflox.weather.data.source.remote.WeatherRemoteDataMapper
import com.koflox.weather.data.source.remote.WeatherRemoteDataMapperImpl
import com.koflox.weather.data.source.remote.WeatherRemoteDataSource
import com.koflox.weather.data.source.remote.WeatherRemoteDataSourceImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

internal val dataModule = module {
    factory<WeatherRemoteDataMapper> {
        WeatherRemoteDataMapperImpl()
    }
    factory<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImpl(
            openWeatherMapService = get(),
            dispatcherIo = Dispatchers.IO,
            entityMapper = get(),
        )
    }
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            remoteDataSource = get(),
        )
    }
}
