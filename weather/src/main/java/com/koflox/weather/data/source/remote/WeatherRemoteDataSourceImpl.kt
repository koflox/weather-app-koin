package com.koflox.weather.data.source.remote

import com.koflox.common_jvm_util.Result
import com.koflox.weather.data.response.open_weather_map.current_weather.CurrentWeatherResponse
import com.koflox.weather.data.response.open_weather_map.forecast.ForecastWeatherResponse
import com.koflox.weather.data.source.WeatherRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherRemoteDataSourceImpl(
    private val openWeatherMapService: OpenWeatherMapService,
    private val ioDispatcher: CoroutineDispatcher,
) : WeatherRemoteDataSource {

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

}
