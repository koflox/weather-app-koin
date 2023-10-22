package com.koflox.weather.view

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.koflox.weather.displayed.DisplayedWeatherItem

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
