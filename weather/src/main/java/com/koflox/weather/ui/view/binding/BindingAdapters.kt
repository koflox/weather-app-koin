package com.koflox.weather.ui.view.binding

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.koflox.weather.ui.displayed.DisplayedWeatherItem
import com.koflox.weather.ui.view.custom.GraphicView

@BindingAdapter("data")
internal fun setGraphicData(graphicView: GraphicView, data: List<DisplayedWeatherItem>?) = data?.run {
    graphicView.setData(this)
}

@BindingAdapter("temp")
internal fun setTemp(textView: AppCompatTextView, temp: Int?) = temp?.run {
    textView.text = when {
        temp > 0 -> "+${temp}"
        else -> temp.toString()
    }
}
