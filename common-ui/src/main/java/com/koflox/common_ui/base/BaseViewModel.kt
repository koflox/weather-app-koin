package com.koflox.common_ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@Suppress("PropertyName")
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

}
