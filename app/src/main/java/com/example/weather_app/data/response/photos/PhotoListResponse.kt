package com.example.weather_app.data.response.photos


import com.google.gson.annotations.SerializedName

data class PhotoListResponse(
    @SerializedName("hits")
    val hits: List<Photo>,
    @SerializedName("totalHits")
    val totalHits: Int
)