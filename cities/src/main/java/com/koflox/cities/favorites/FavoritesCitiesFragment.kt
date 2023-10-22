package com.koflox.cities.favorites

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.koflox.cities.CitiesNavigator
import com.koflox.cities.R
import com.koflox.cities.databinding.FragmentFavoriteCitiesBinding
import com.koflox.common_ui.EventObserver
import com.koflox.common_ui.base.BaseFragment
import com.koflox.common_ui.base.UniversalItemDecorator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.koflox.common_android_res.R as commonResR

class FavoritesCitiesFragment : BaseFragment<FragmentFavoriteCitiesBinding>(), PopupMenu.OnMenuItemClickListener {

    private val viewModel by viewModel<FavoritesCitiesViewModel>()
    private val citiesNavigator: CitiesNavigator by inject()

    private lateinit var favoriteCitiesAdapter: FavoriteCitiesAdapter

    override fun getLayoutId() = R.layout.fragment_favorite_cities

    override fun initViews() {
        favoriteCitiesAdapter = FavoriteCitiesAdapter(viewModel)
        dataBinding.viewModel = viewModel
        dataBinding.rvFavoriteCities.apply {
            emptyView = dataBinding.tvPlaceholderFavoriteCities
            val spacing = resources.getDimensionPixelSize(commonResR.dimen.indent_medium)
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteCitiesAdapter
            addItemDecoration(UniversalItemDecorator(spacing, UniversalItemDecorator.Type.VERTICAL))
        }
    }

    override fun addViewObservers() {
        viewModel.selectedCity.observe(this, EventObserver {
            val (city, position, showContextMenu) = it
            when {
                showContextMenu -> {
                    val itemView = dataBinding.rvFavoriteCities.layoutManager?.getChildAt(position) ?: return@EventObserver
                    showFavoriteCityContextMenu(itemView)
                }

                else -> navigateToWeather(city.id)
            }
        })
        viewModel.onGetWeather.observe(this, EventObserver {
            navigateToWeather(it)
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.actionWeather -> {
                viewModel.onActionGetWeather()
                true
            }

            R.id.actionRemove -> {
                viewModel.onActionRemove()
                true
            }

            else -> false
        }
    }

    private fun showFavoriteCityContextMenu(itemView: View) {
        context?.run {
            PopupMenu(this, itemView).run {
                menuInflater.inflate(R.menu.menu_fav_city, menu)
                setOnMenuItemClickListener(this@FavoritesCitiesFragment)
                show()
            }
        }
    }

    private fun navigateToWeather(cityId: Int) {
        citiesNavigator.goToWeatherDetails(
            fragment = this,
            placeName = null,
            cityId = cityId.toString(),
        )

    }

}
