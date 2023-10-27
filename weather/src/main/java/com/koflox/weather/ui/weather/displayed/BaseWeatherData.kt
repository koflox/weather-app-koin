package com.koflox.weather.ui.weather.displayed

import androidx.annotation.DrawableRes

internal sealed class WeatherUiModel {

    companion object {
        const val MAIN = 0
        const val HOURLY = 1
        const val DETAILS = 2
        const val PRECIPITATION = 3
        const val FORECAST = 4
    }

    abstract fun getDataType(): Int

}

internal data class MainWeatherUiModel(
    val temp: Int,
    val tempMin: Int,
    val tempMax: Int,
    val tempUnitMain: String,
    val tempUnitExtra: String = "",
    val weatherIconUrl: String? = null,
    val weatherDescription: String,
    val dayName: String = "",
    val chanceOfPrecipitation: String = ""
) : WeatherUiModel() {

    override fun getDataType() = MAIN

}

internal data class HourlyWeatherUiModel(
    val sectionTitle: String,
    val values: List<DisplayedWeatherItem>
) : WeatherUiModel() {

    override fun getDataType() = HOURLY

}

internal data class DetailsWeatherUiModel(
    val sectionTitle: String,
    val values: List<DetailsWeatherDataItem>
) : WeatherUiModel() {

    override fun getDataType() = DETAILS

}

internal data class PrecipitationWeatherUiModel(
    val sectionTitle: String,
    val values: List<DisplayedWeatherItem>
) : WeatherUiModel() {

    override fun getDataType() = PRECIPITATION

}

internal data class ForecastWeatherUiModel(
    val sectionTitle: String,
    val values: List<MainWeatherUiModel>
) : WeatherUiModel() {

    override fun getDataType() = FORECAST

}

internal data class DetailsWeatherDataItem(
    @DrawableRes val resourceId: Int,
    val desc: String,
    val value: String,
    val unit: String = ""
)
