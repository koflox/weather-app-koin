package com.koflox.weather.data.entity.remote

import com.google.gson.annotations.SerializedName

internal data class ForecastResponse(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Int,
    @SerializedName("list")
    val list: List<DayWeatherResponse>,
    @SerializedName("city")
    val city: CityResponse
)
