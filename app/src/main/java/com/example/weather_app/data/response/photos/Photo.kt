package com.example.weather_app.data.response.photos


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageHeight")
    val imageHeight: Int,
    @SerializedName("imageSize")
    val imageSize: Int,
    @SerializedName("imageWidth")
    val imageWidth: Int,
    @SerializedName("largeImageURL")
    val largeImageURL: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("type")
    val type: String
)