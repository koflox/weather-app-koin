package com.koflox.weather.displayed

import androidx.annotation.DrawableRes

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
    val weatherIconUrl: String? = null,
    val weatherDescription: String,
    val dayName: String = "",
    val chanceOfPrecipitation: String = ""
) : WeatherData() {

    override fun getDataType() = MAIN

}

data class HourlyWeatherData(
    val sectionTitle: String,
    val values: List<DisplayedWeatherItem>
) : WeatherData() {

    override fun getDataType() = HOURLY

}

data class DetailsWeatherData(
    val sectionTitle: String,
    val values: List<DetailsWeatherDataItem>
) : WeatherData() {

    override fun getDataType() = DETAILS

}

data class PrecipitationWeatherData(
    val sectionTitle: String,
    val values: List<DisplayedWeatherItem>
) : WeatherData() {

    override fun getDataType() = PRECIPITATION

}

data class ForecastWeatherData(
    val sectionTitle: String,
    val values: List<MainWeatherData>
) : WeatherData() {

    override fun getDataType() = FORECAST

}

data class DetailsWeatherDataItem(
    @DrawableRes val resourceId: Int,
    val desc: String,
    val value: String,
    val unit: String = ""
)
