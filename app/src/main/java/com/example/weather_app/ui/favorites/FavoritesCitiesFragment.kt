package com.example.weather_app.ui.favorites

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.weather_app.R
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.ui.base.BaseFragment
import com.example.weather_app.ui.base.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_favorite_cities.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesCitiesFragment : BaseFragment(), PopupMenu.OnMenuItemClickListener {

    private val viewModel by viewModel<FavoritesCitiesViewModel>()

    private val favoriteCityAdapterItemClickListener =
        object : FavoriteCitiesAdapter.OnItemClickListener {
            override fun onOptionsClick(view: View, city: FavoriteCity) {
                context?.run {
                    viewModel.selectedCity = city
                    PopupMenu(this, view).run {
                        menuInflater.inflate(R.menu.menu_fav_city, menu)
                        setOnMenuItemClickListener(this@FavoritesCitiesFragment)
                        show()
                    }
                }
            }
        }

    private val favoriteCitiesAdapter = FavoriteCitiesAdapter(favoriteCityAdapterItemClickListener)

    override fun getLayoutId() = R.layout.fragment_favorite_cities

    override fun initViews() {
        rvFavoriteCities.apply {
            val spanCount = resources.getInteger(R.integer.favorite_cities_span_count)
            val itemsMargin = resources.getDimensionPixelSize(R.dimen.indent_medium)
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = favoriteCitiesAdapter
            addItemDecoration(GridSpacingItemDecoration(spanCount, itemsMargin))
        }
    }

    override fun addViewObservers() {
        viewModel.run {
            favoriteCities.observe(viewLifecycleOwner, Observer {
                favoriteCitiesAdapter.setData(it)
            })
            showAddCityHint.observe(viewLifecycleOwner, Observer {

            })
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.actionForecast -> {
                true
            }
            R.id.actionHistory -> {
                true
            }
            R.id.actionRemove -> {
                viewModel.deleteFavoriteCity()
                true
            }
            else -> false
        }
    }


}
