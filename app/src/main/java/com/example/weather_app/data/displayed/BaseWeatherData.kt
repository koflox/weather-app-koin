package com.example.weather_app.data.displayed

import com.example.weather_app.data.response.open_weather_map.current_weather.DetailsWeatherDataItem

sealed class WeatherData {

    companion object {
        const val MAIN = 0
        const val HOURLY = 1
        const val DETAILS = 2
        const val PRECIPITATION = 3
        const val FORECAST = 4
    }

    abstract fun getDataType(): Int

}

data class MainWeatherData(
        val temp: Int,
        val tempMin: Int,
        val tempMax: Int,
        val tempUnitMain: String,
        val tempUnitExtra: String = "",
        val weatherIconRes: Int,
        val weatherDescription: String,
        val dayName: String = "",
        val chanceOfPrecipitation: String = ""
) : WeatherData() {

    override fun getDataType() = MAIN

}

data class HourlyWeatherData(
        // time : temp
        val values: List<Pair<String, Int>>
) : WeatherData() {

    override fun getDataType() = HOURLY

}

data class DetailsWeatherData(
        val values: List<DetailsWeatherDataItem>
) : WeatherData() {

    override fun getDataType() = DETAILS

}

data class PrecipitationWeatherData(
        // time : amount of precipitation in mm for last 3 hours
        val values: List<Pair<String, Int>>
) : WeatherData() {

    override fun getDataType() = PRECIPITATION

}

data class ForecastWeatherData(
        val values: List<MainWeatherData>
) : WeatherData() {

    override fun getDataType() = FORECAST

}