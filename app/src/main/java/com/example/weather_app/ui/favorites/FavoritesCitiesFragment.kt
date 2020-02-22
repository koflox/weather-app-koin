package com.example.weather_app.ui.favorites

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentFavoriteCitiesBinding
import com.example.weather_app.ui.base.BaseFragment
import com.example.weather_app.ui.base.UniversalItemDecorator
import com.example.weather_app.util.EventObserver
import kotlinx.android.synthetic.main.fragment_favorite_cities.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesCitiesFragment : BaseFragment<FragmentFavoriteCitiesBinding>(), PopupMenu.OnMenuItemClickListener {

    private val viewModel by viewModel<FavoritesCitiesViewModel>()

    private lateinit var favoriteCitiesAdapter: FavoriteCitiesAdapter

    override fun getLayoutId() = R.layout.fragment_favorite_cities

    override fun initViews() {
        favoriteCitiesAdapter = FavoriteCitiesAdapter(viewModel)
        dataBinding.viewModel = viewModel
        rvFavoriteCities.apply {
            emptyView = tvPlaceholderFavoriteCities
            val spacing = resources.getDimensionPixelSize(R.dimen.indent_medium)
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
                    val itemView = rvFavoriteCities.layoutManager?.getChildAt(position) ?: return@EventObserver
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
        val action = FavoritesCitiesFragmentDirections.actionFavoritesCitiesFragmentToCurrentWeatherFragment(cityId)
        findNavController().navigate(action)
    }

}
