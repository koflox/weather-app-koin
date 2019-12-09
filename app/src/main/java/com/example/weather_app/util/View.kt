package com.example.weather_app.util

import android.graphics.Typeface
import android.os.Build
import android.text.TextPaint
import android.text.style.MetricAffectingSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
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

fun toView(@LayoutRes resId: Int, parent: ViewGroup): View = LayoutInflater.from(parent.context)
        .inflate(resId, parent, false)

fun getTypefaceSpan(typeface: Typeface): MetricAffectingSpan {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> TypefaceSpan(typeface)
        else -> CompatTypefaceSpan(typeface)
    }
}

class CompatTypefaceSpan(private val typeface: Typeface) : MetricAffectingSpan() {
    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
    }
}