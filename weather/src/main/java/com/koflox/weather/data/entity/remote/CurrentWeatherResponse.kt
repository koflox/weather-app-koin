package com.koflox.weather.data.entity.remote

import com.google.gson.annotations.SerializedName

internal data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coord: CoordResponse,
    @SerializedName("weather")
    val weatherDescriptions: List<WeatherDescResponse>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: MainParamsResponse,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind")
    val wind: WindResponse,
    @SerializedName("clouds")
    val clouds: CloudsResponse,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("sys")
    val sys: WeatherMetaResponse,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("rain")
    val rain: RainResponse?,
    @SerializedName("snow")
    val snow: SnowResponse?,
)
