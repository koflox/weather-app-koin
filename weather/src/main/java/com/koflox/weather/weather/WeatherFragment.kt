package com.koflox.weather.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.koflox.common_ui.EventObserver
import com.koflox.common_ui.base.BaseFragment
import com.koflox.common_ui.setSupportActionBarSubtitle
import com.koflox.common_ui.setSupportActionBarTitle
import com.koflox.common_ui.showToast
import com.koflox.weather.R
import com.koflox.weather.databinding.FragmentWeatherBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.koflox.common_android_res.R as commonResR

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    private val args by navArgs<WeatherFragmentArgs>()

    private val viewModel by viewModel<WeatherViewModel>()

    private lateinit var weatherAdapter: WeatherAdapter

    override fun getLayoutId() = R.layout.fragment_weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCurrentWeather(args.placeToSearch, args.cityIdToSearch)
    }

    override fun initViews() {
        weatherAdapter = WeatherAdapter(viewModel)
        dataBinding.viewModel = viewModel
        setSupportActionBarTitle(commonResR.string.text_loading)
        setHasOptionsMenu(true)
        dataBinding.rvWeatherData.apply {
            emptyView = dataBinding.tvPlaceholderWeather
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
        viewModel.displayedToolbarInfo.observe(this) {
            setSupportActionBarTitle(it.first)
            setSupportActionBarSubtitle(it.second)
        }
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
