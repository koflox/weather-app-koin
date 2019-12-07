package com.example.weather_app.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        afterCreate(savedInstanceState)
        initViews()
        addObservers()
    }

    abstract fun afterCreate(savedInstanceState: Bundle?)

    abstract fun initViews()

    abstract fun addObservers()

}