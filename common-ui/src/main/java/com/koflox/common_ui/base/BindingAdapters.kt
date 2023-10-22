package com.koflox.common_ui.base

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koflox.common_jvm_util.notNull
import com.koflox.common_ui.loadFromResource
import com.koflox.common_ui.loadFromUrl

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setData(recyclerView: RecyclerView, data: List<T>?) {
    notNull(recyclerView.adapter as? BindableAdapter<T>, data) { adapter, list ->
        adapter.setData(list)
    }
}

@BindingAdapter("srcUrl")
fun setSrcFromUrl(imageView: AppCompatImageView, url: String?) = url?.run {
    imageView.loadFromUrl(url)
}

@BindingAdapter("srcRes")
fun setSrcFromUrl(imageView: AppCompatImageView, @DrawableRes resId: Int?) = resId?.run {
    imageView.loadFromResource(resId)
}