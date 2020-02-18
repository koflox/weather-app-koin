package com.example.weather_app.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes resId: Int) = showToast(getString(resId))

fun Fragment.setSupportActionBarTitleTittle(title: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

fun Fragment.setSupportActionBarTitleTittle(@StringRes stringRes: Int) {
    setSupportActionBarTitleTittle(getString(stringRes))
}