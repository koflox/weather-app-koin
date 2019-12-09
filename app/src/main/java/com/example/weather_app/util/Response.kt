package com.example.weather_app.util

import com.example.weather_app.data.entity.DisplayableWeatherInfo
import com.example.weather_app.data.entity.FavoriteCity

//fun CurrentWeatherResponse.toFavoriteCity(): FavoriteCity {
//    return location.run {
//        FavoriteCity(
//                name,
//                region,
//                country,
//                lat,
//                lng,
//                System.currentTimeMillis()
//        )
//    }
//}

fun DisplayableWeatherInfo.toFavoriteCity() = FavoriteCity(
        cityName,
        region,
        country,
        lat,
        lng,
        System.currentTimeMillis()
)