package com.example.weather_app.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.weather_app.R

//fun ImageView.loadFromUrl(url: String?) =
fun ImageView.loadFromUrl(url: String?, @DrawableRes resIdOnError: Int = R.drawable.ic_city_placeholder) =
    if (url.isNullOrBlank())
        loadFromResource(resIdOnError)
    else
        Glide.with(this.context)
            .applyDefaultRequestOptions(RequestOptions().error(resIdOnError))
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)

fun ImageView.loadFromResource(@DrawableRes resId: Int) =
    Glide.with(this.context)
        .load(resId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)