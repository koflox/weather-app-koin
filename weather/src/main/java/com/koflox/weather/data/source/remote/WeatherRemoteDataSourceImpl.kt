package com.koflox.weather.data.source.remote

import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.SystemOfMeasurement
import com.koflox.weather.domain.use_case.weather_info.WeatherQueryParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class WeatherRemoteDataSourceImpl(
    private val openWeatherMapService: OpenWeatherMapService,
    private val dispatcherIo: CoroutineDispatcher,
    private val entityMapper: WeatherRemoteDataMapper,
) : WeatherRemoteDataSource {

    override suspend fun getCurrentWeather(
        queryParam: WeatherQueryParam,
        unit: SystemOfMeasurement,
    ): CurrentWeather {
        return withContext(dispatcherIo) {
            val unitRemote = entityMapper.mapUnit(unit)
            val response = when (queryParam) {
                is WeatherQueryParam.City -> openWeatherMapService.getCurrentWeatherByCityId(
                    cityId = queryParam.value,
                    unit = unitRemote,
                )

                is WeatherQueryParam.Place -> openWeatherMapService.getCurrentWeatherByPlaceName(
                    place = queryParam.value,
                    unit = unitRemote,
                )
            }
            entityMapper.mapCurrentWeather(response)
        }
    }

    override suspend fun getForecast(
        queryParam: WeatherQueryParam,
        unit: SystemOfMeasurement,
    ): Forecast {
        return withContext(dispatcherIo) {
            val unitRemote = entityMapper.mapUnit(unit)
            val response = when (queryParam) {
                is WeatherQueryParam.City -> openWeatherMapService.getForecastByCityId(
                    cityId = queryParam.value,
                    unit = unitRemote,
                )

                is WeatherQueryParam.Place -> openWeatherMapService.getForecastByPlaceName(
                    place = queryParam.value,
                    unit = unitRemote,
                )
            }
            entityMapper.mapForecast(response)
        }
    }

}
