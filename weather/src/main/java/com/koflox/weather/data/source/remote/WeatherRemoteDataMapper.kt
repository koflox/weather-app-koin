package com.koflox.weather.data.source.remote

import com.koflox.weather.data.entity.remote.CurrentWeatherResponse
import com.koflox.weather.data.entity.remote.ForecastResponse
import com.koflox.weather.data.entity.remote.MainParamsResponse
import com.koflox.weather.data.entity.remote.RainResponse
import com.koflox.weather.data.entity.remote.SnowResponse
import com.koflox.weather.data.entity.remote.SystemOfMeasurementRemote
import com.koflox.weather.data.entity.remote.WeatherDescResponse
import com.koflox.weather.data.entity.remote.WindResponse
import com.koflox.weather.data.source.remote.OpenWeatherMapService.Companion.BASE_URL_DEFAULT
import com.koflox.weather.data.source.remote.OpenWeatherMapService.Companion.PATH_IMAGES
import com.koflox.weather.domain.entity.City
import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.DayWeather
import com.koflox.weather.domain.entity.Forecast
import com.koflox.weather.domain.entity.MainParams
import com.koflox.weather.domain.entity.Rain
import com.koflox.weather.domain.entity.Snow
import com.koflox.weather.domain.entity.SystemOfMeasurement
import com.koflox.weather.domain.entity.WeatherDesc
import com.koflox.weather.domain.entity.Wind

internal interface WeatherRemoteDataMapper {
    fun mapCurrentWeather(response: CurrentWeatherResponse): CurrentWeather
    fun mapForecast(response: ForecastResponse): Forecast
    fun mapUnit(data: SystemOfMeasurement): SystemOfMeasurementRemote
}

internal class WeatherRemoteDataMapperImpl : WeatherRemoteDataMapper {
    override fun mapCurrentWeather(response: CurrentWeatherResponse): CurrentWeather {
        return CurrentWeather(
            weather = mapWeatherDescriptions(response.weatherDescriptions),
            main = mapMainParams(response.main),
            wind = mapWind(response.wind),
            dt = response.dt,
            timezone = response.timezone,
            rain = response.rain?.let(::mapRain),
            snow = response.snow?.let(::mapSnow),
            city = City(
                id = response.id,
                name = response.cityName,
                country = response.sys.country,
                timezone = response.timezone,
            ),
        )
    }

    override fun mapForecast(response: ForecastResponse): Forecast {
        return Forecast(
            list = response.list.map { dayWeatherRemote ->
                DayWeather(
                    dateUtc = dayWeatherRemote.dateUtc,
                    main = mapMainParams(dayWeatherRemote.main),
                    weatherDescriptions = mapWeatherDescriptions(dayWeatherRemote.weatherDescriptions),
                    rain = dayWeatherRemote.rain?.let(::mapRain),
                    snow = dayWeatherRemote.snow?.let(::mapSnow),
                )
            },
            city = City(
                id = response.city.id,
                name = response.city.name,
                country = response.city.country,
                timezone = response.city.timezone,
            ),
        )
    }

    override fun mapUnit(data: SystemOfMeasurement): SystemOfMeasurementRemote {
        return when (data) {
            SystemOfMeasurement.METRIC -> SystemOfMeasurementRemote.METRIC
            SystemOfMeasurement.IMPERIAL -> SystemOfMeasurementRemote.IMPERIAL
        }
    }

    private fun mapWind(response: WindResponse): Wind = Wind(
        speed = response.speed,
    )

    private fun mapMainParams(response: MainParamsResponse): MainParams = MainParams(
        temp = response.temp,
        pressure = response.pressure,
        humidity = response.humidity,
        tempMin = response.tempMin,
        tempMax = response.tempMax,
    )

    private fun mapRain(data: RainResponse): Rain = data.run {
        Rain(
            h = h,
        )
    }

    private fun mapSnow(data: SnowResponse): Snow = data.run {
        Snow(
            h = h,
        )
    }

    private fun mapWeatherDescriptions(data: List<WeatherDescResponse>): List<WeatherDesc> = data.map { descRemote ->
        WeatherDesc(
            main = descRemote.main,
            weatherIconUrl = mapIconNameToUrl(descRemote.icon),
        )
    }

    private fun mapIconNameToUrl(
        iconName: String,
        extension: String = OpenWeatherMapService.ICON_EXTENSION_PNG
    ): String {
        return "$BASE_URL_DEFAULT$PATH_IMAGES$iconName.$extension"
    }

}
