package com.example.weather_app.data.entity

data class DisplayableWeatherInfo(
        val cityName: String,
        val region: String,
        val country: String,
        val lat: Double,
        val lng: Double,
        val date: String,
        val weatherDescription: String,
        val temperature: String,
        val windSpeed: String,
        val pressure: String,
        val humidity: String,
        var isFavoriteCity: Boolean
)