package com.example.weather_app.extensions

import com.example.weather_app.data.entity.DisplayableWeatherInfo
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.data.response.current_weather.CurrentWeatherResponse

fun CurrentWeatherResponse.toFavoriteCity(): FavoriteCity {
    return location.run {
        FavoriteCity(
                name,
                region,
                country,
                lat,
                lng,
                System.currentTimeMillis()
        )
    }
}

fun DisplayableWeatherInfo.toFavoriteCity() = FavoriteCity(
        cityName,
        region,
        country,
        lat,
        lng,
        System.currentTimeMillis()
)