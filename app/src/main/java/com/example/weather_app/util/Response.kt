package com.example.weather_app.util

import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.data.response.open_weather_map.current_weather.CurrentWeatherResponse

fun CurrentWeatherResponse.toFavoriteCity() = FavoriteCity(
    id,
    cityName,
    sys.country,
    coord.lat,
    coord.lon,
    System.currentTimeMillis()
)