package com.example.weather_app.data.displayed

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
        val temp: String,
        val tempMin: String,
        val tempMax: String,
        val weatherIconRes: Int,
        val weatherDescription: String,
        val dayName: String = "",
        val chanceOfPrecipitation: String = ""
) : WeatherData() {

    override fun getDataType() = MAIN

}

data class HourlyWeatherData(
        // time : temp
        val values: List<Pair<String, String>>
) : WeatherData() {

    override fun getDataType() = HOURLY

}

data class DetailsWeatherData(
        val tempFeelsLike: String,
        val wind: String,
        val humidity: String,
        val pressure: String,
        val visibility: String,
        val dewPoint: String
) : WeatherData() {

    override fun getDataType() = DETAILS

}

data class PrecipitationWeatherData(
        // time : chance of precipitation
        val values: List<Pair<String, String>>
) : WeatherData() {

    override fun getDataType() = PRECIPITATION

}

data class ForecastWeatherData(
        val values: List<MainWeatherData>
) : WeatherData() {

    override fun getDataType() = FORECAST

}