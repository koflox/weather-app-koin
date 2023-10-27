package com.koflox.cities.search

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koflox.common_ui.Event

class SearchViewModel : ViewModel() {

    companion object {
        private const val SEARCH_DELAY = 350L
    }

    private val _showWeather = MutableLiveData<Event<String>>()
    val showWeather: LiveData<Event<String>> = _showWeather

    private lateinit var searchQuery: String
    private val searchHandler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        //todo getPlaces()
    }

    override fun onCleared() {
        searchHandler.removeCallbacks(searchRunnable)
    }

    fun onQueryTextChange(query: String?) {
        if (query.isNullOrBlank()) return
        searchQuery = query
        searchHandler.run {
            removeCallbacks(searchRunnable)
            postDelayed(searchRunnable, SEARCH_DELAY)
        }
    }

    fun onQueryTextSubmit(query: String?) {
        if (query.isNullOrBlank()) return
        _showWeather.value = Event(query)
    }


}
