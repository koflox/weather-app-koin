package com.koflox.weather.data.entity.remote

import com.google.gson.annotations.SerializedName

internal data class WeatherMetaResponse(
    @SerializedName("type")
    val type: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int
)

internal data class MainParamsResponse(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double
)

internal data class WeatherDescResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

internal data class CloudsResponse(
    @SerializedName("all")
    val all: Int
)

internal data class WindResponse(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int
)

internal data class RainResponse(
    @SerializedName("3h")
    val h: Double
)

internal data class SnowResponse(
    @SerializedName("3h")
    val h: Double
)
