package com.example.weather_app.extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.weather_app.R

fun ImageView.loadFromUrl(url: String?, @DrawableRes resIdOnError: Int = R.drawable.ic_city_placeholder) = when {
    url.isNullOrBlank() -> loadFromResource(resIdOnError)
    else -> Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions().error(resIdOnError))
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadFromResource(@DrawableRes resId: Int) = Glide.with(context)
        .load(resId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun toView(@LayoutRes resId: Int, parent: ViewGroup) = LayoutInflater.from(parent.context)
        .inflate(resId, parent, false)