package com.example.weather_app.ui.current_weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentWeatherBinding
import com.example.weather_app.ui.base.BaseFragment
import com.example.weather_app.util.EventObserver
import com.example.weather_app.util.setSupportActionBarSubtitle
import com.example.weather_app.util.setSupportActionBarTitle
import com.example.weather_app.util.showToast
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentWeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    private val args by navArgs<CurrentWeatherFragmentArgs>()

    private val viewModel by viewModel<CurrentWeatherViewModel>()

    private lateinit var weatherAdapter: WeatherAdapter

    override fun getLayoutId() = R.layout.fragment_weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCurrentWeather(args.placeToSearch, args.cityIdToSearch)
    }

    override fun initViews() {
        weatherAdapter = WeatherAdapter(viewModel)
        dataBinding.viewModel = viewModel
        setSupportActionBarTitle(R.string.text_loading)
        setHasOptionsMenu(true)
        rvWeatherData.apply {
            emptyView = tvPlaceholderWeather
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun addViewObservers() {
        viewModel.message.observe(viewLifecycleOwner, EventObserver { msg ->
            context?.showToast(msg)
        })
        viewModel.isCityAddedToFavorite.observe(this, EventObserver {
            activity?.invalidateOptionsMenu()
        })
        viewModel.displayedToolbarInfo.observe(this, Observer {
            setSupportActionBarTitle(it.first)
            setSupportActionBarSubtitle(it.second)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuRes = when (viewModel.isCityAddedToFavorite.value?.peekContent()) {
            true -> R.menu.menu_city_is_added
            false -> R.menu.menu_city_is_not_added
            null -> return
        }
        inflater.inflate(menuRes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_remove_city,
            R.id.action_add_city -> {
                viewModel.onAddDeleteOptionClick()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}