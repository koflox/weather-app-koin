package com.example.weather_app.ui

import com.example.weather_app.R
import com.koflox.common_ui.base.BaseActivity

class WeatherActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initViews() {
        setSupportActionBar(findViewById(R.id.toolbar))
    }

}
