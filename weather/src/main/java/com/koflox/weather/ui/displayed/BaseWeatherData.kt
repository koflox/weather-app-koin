package com.koflox.weather.ui.displayed

import androidx.annotation.DrawableRes

internal sealed class WeatherData {

    companion object {
        const val MAIN = 0
        const val HOURLY = 1
        const val DETAILS = 2
        const val PRECIPITATION = 3
        const val FORECAST = 4
    }

    abstract fun getDataType(): Int

}

internal data class MainWeatherData(
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

internal data class HourlyWeatherData(
    val sectionTitle: String,
    val values: List<DisplayedWeatherItem>
) : WeatherData() {

    override fun getDataType() = HOURLY

}

internal data class DetailsWeatherData(
    val sectionTitle: String,
    val values: List<DetailsWeatherDataItem>
) : WeatherData() {

    override fun getDataType() = DETAILS

}

internal data class PrecipitationWeatherData(
    val sectionTitle: String,
    val values: List<DisplayedWeatherItem>
) : WeatherData() {

    override fun getDataType() = PRECIPITATION

}

internal data class ForecastWeatherData(
    val sectionTitle: String,
    val values: List<MainWeatherData>
) : WeatherData() {

    override fun getDataType() = FORECAST

}

internal data class DetailsWeatherDataItem(
    @DrawableRes val resourceId: Int,
    val desc: String,
    val value: String,
    val unit: String = ""
)
