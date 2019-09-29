package com.example.weather_app.ui.base.shared

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.weather_app.data.entity.DisplayableWeatherInfo
import com.example.weather_app.ui.base.BaseViewModel

class WeatherSharedViewModel(app: Application) : BaseViewModel(app) {

    val weatherInfo = MutableLiveData<DisplayableWeatherInfo>()

}