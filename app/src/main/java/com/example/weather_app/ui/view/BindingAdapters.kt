package com.example.weather_app.ui.view

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.data.displayed.DisplayedWeatherItem
import com.example.weather_app.ui.base.BindableAdapter
import com.example.weather_app.util.loadFromUrl
import com.example.weather_app.util.notNull

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setData(recyclerView: RecyclerView, data: List<T>?) {
    notNull(recyclerView.adapter as? BindableAdapter<T>, data) { adapter, list ->
        adapter.setData(list)
    }
}

@BindingAdapter("data")
fun setGraphicData(graphicView: GraphicView, data: List<DisplayedWeatherItem>?) = data?.run {
    graphicView.setData(this)
}

@BindingAdapter("temp")
fun setTemp(textView: AppCompatTextView, temp: Int?) = temp?.run {
    textView.text = when {
        temp > 0 -> "+${temp}"
        else -> temp.toString()
    }
}

@BindingAdapter("srcUrl")
fun setSrcFromUrl(imageView: AppCompatImageView, url: String?) = url?.run {
    imageView.loadFromUrl(url)
}