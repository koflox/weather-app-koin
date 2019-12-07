package com.example.weather_app.ui.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.weather_app.R
import com.example.weather_app.extensions.nonNull
import com.example.weather_app.extensions.showToast
import com.example.weather_app.ui.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherFragment : BaseFragment(), OnMapReadyCallback {

    private val viewModel by sharedViewModel<WeatherViewModel>()
//    private val weatherInfoSharedViewModel by sharedViewModel<WeatherSharedViewModel>()

    private var googleMap: GoogleMap? = null

    override fun getLayoutId() = R.layout.fragment_weather

    override fun afterViewCreated(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        setHasOptionsMenu(true)

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun addObservers() {
        viewModel.run {
            weatherInfo.observe(viewLifecycleOwner, Observer {
                nonNull(googleMap, it) { map, currentWeather ->
                    map.run {
                        val location = LatLng(currentWeather.lat, currentWeather.lng)
                        addMarker(MarkerOptions().position(location).title(currentWeather.cityName))
                        moveCamera(CameraUpdateFactory.newLatLng(location))
                    }
//                    weatherInfoSharedViewModel.weatherInfo.value = currentWeather
                }
            })
            loading.observe(viewLifecycleOwner, Observer { isLoading ->
//                weatherInfoSharedViewModel.loading.value = isLoading
            })
            message.observe(viewLifecycleOwner, Observer { msg ->
                context?.showToast(msg)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weather, menu)
        val searchView = menu.findItem(R.id.actionWeatherSearch)?.actionView as? SearchView
        searchView?.apply {
            queryHint = getString(R.string.search)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
//                    query?.run { viewModel.getWeather(this) }
                    //todo hide keyboard
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.run { viewModel.onQueryTextChange(this) }
                    return true
                }
            })
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.run {
            this@WeatherFragment.googleMap = this
            setOnMapClickListener { coordinates ->
                //                viewModel.getWeatherByCoordinates(coordinates)
            }
        }
        viewModel.setMapIsReady()
    }

}
