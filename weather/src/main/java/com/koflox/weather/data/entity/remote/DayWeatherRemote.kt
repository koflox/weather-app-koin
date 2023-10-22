package com.koflox.weather.data.entity.remote

import com.google.gson.annotations.SerializedName

internal data class DayWeatherResponse(
    @SerializedName("dt")
    val dateUtc: Int,
    @SerializedName("main")
    val main: MainParamsResponse,
    @SerializedName("weather")
    val weatherDescriptions: List<WeatherDescResponse>,
    @SerializedName("clouds")
    val clouds: CloudsResponse,
    @SerializedName("wind")
    val wind: WindResponse,
    @SerializedName("dt_txt")
    val dtTxt: String,
    @SerializedName("rain")
    val rain: RainResponse?,
    @SerializedName("snow")
    val snow: SnowResponse?
)
