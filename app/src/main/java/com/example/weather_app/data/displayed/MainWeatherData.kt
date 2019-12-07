package com.example.weather_app.data.displayed

data class MainWeatherData(
        val temp: String,
        val tempMin: String,
        val tempMax: String,
        val weatherIconRes: Int,
        val weatherDescription: String,
        val dayName: String = "",
        val chanceOfPrecipitation: String = ""
)