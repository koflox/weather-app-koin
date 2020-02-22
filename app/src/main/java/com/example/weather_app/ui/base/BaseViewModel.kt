package com.example.weather_app.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather_app.util.Event

@Suppress("PropertyName")
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    //todo develop a DisplayableException to display error data in UI
    protected val _error = MutableLiveData<Event<Exception>>()
    val error: LiveData<Event<Exception>> = _error

    //todo add encapsulation
    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

}