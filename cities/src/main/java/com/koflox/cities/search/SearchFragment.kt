package com.koflox.cities.search

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import com.koflox.cities.CitiesNavigator
import com.koflox.cities.R
import com.koflox.cities.databinding.FragmentSearchBinding
import com.koflox.common_ui.EventObserver
import com.koflox.common_ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel by viewModel<SearchViewModel>()
    private val citiesNavigator: CitiesNavigator by inject()

    override fun getLayoutId() = R.layout.fragment_search

    override fun initViews() {
        setHasOptionsMenu(true)
    }

    override fun addObservers() {
        viewModel.showWeather.observe(this, EventObserver { placeToGetWeather ->
            citiesNavigator.goToWeatherDetails(
                fragment = this,
                placeName = placeToGetWeather,
                cityId = null,
            )
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
