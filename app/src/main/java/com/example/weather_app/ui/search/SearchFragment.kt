package com.example.weather_app.ui.search

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.weather_app.R
import com.example.weather_app.ui.base.BaseFragment
import com.example.weather_app.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {

    private val viewModel by viewModel<SearchViewModel>()

    override fun getLayoutId() = R.layout.fragment_search

    override fun initViews() {
        setHasOptionsMenu(true)
    }

    override fun addObservers() {
        viewModel.showWeather.observe(this, EventObserver { placeToGetWeather ->
            val action = SearchFragmentDirections.actionSearchFragmentToCurrentWeatherFragment(placeToGetWeather)
            findNavController().navigate(action)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weather, menu)
        val searchView = menu.findItem(R.id.actionWeatherSearch)?.actionView as? SearchView
        searchView?.apply {
            setIconifiedByDefault(false)
            queryHint = getString(R.string.search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onQueryTextSubmit(query)
                    //todo hide keyboard
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onQueryTextChange(newText)
                    return true
                }
            })
        }
    }

}
