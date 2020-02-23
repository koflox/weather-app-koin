package com.example.weather_app.ui.weather

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.*
import com.example.weather_app.databinding.*
import com.example.weather_app.ui.base.UniversalItemDecorator
import kotlinx.android.synthetic.main.item_weather_data_details.view.*
import kotlinx.android.synthetic.main.item_weather_data_forecast.view.*

abstract class BaseWeatherDataVH(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bind(viewModel: WeatherViewModel, data: WeatherData)

}

class MainWeatherDataVH(private val dataBinding: ItemWeatherDataMainBinding) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        dataBinding.apply {
            this.viewModel = viewModel
            item = data as MainWeatherData
            executePendingBindings()
        }
    }

}

class HourlyWeatherDataVH(
    private val dataBinding: ItemWeatherDataHourlyBinding,
    private val parent: View
) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        dataBinding.root.minimumHeight = parent.measuredHeight / 10 * 2
        dataBinding.apply {
            this.viewModel = viewModel
            item = data as HourlyWeatherData
            executePendingBindings()
        }
    }

}

class DetailsWeatherDataVH(
    private val dataBinding: ItemWeatherDataDetailsBinding,
    private val adapterDetailsWeatherData: DetailsWeatherDataAdapter
) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        dataBinding.root.rvDetailsWeatherData.apply {
            val spacing = context.resources.getDimensionPixelSize(R.dimen.common_list_spacing)
            val spanCount = context.resources.getInteger(R.integer.span_count_details_weather_data)
            addItemDecoration(UniversalItemDecorator(spacing, UniversalItemDecorator.Type.GRID, spanCount))
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = adapterDetailsWeatherData
        }
        dataBinding.apply {
            this.viewModel = viewModel
            item = data as DetailsWeatherData
            executePendingBindings()
        }
    }

}

class PrecipitationWeatherDataVH(
    private val dataBinding: ItemWeatherDataPrecipitationBinding,
    private val parent: View
) : BaseWeatherDataVH(dataBinding.root) {
    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        dataBinding.root.minimumHeight = (parent.measuredHeight / 10 * 2.5F).toInt()
        dataBinding.apply {
            this.viewModel = viewModel
            item = data as PrecipitationWeatherData
            executePendingBindings()
        }
    }

}

class ForecastWeatherDataVH(
    private val dataBinding: ItemWeatherDataForecastBinding,
    private val adapterForecastWeatherData: ForecastWeatherDataAdapter
) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        data as ForecastWeatherData
        dataBinding.root.rvForecastsWeatherData.apply {
            layoutManager = GridLayoutManager(context, data.values.size)
            adapter = adapterForecastWeatherData
        }
        dataBinding.apply {
            this.viewModel = viewModel
            item = data
            executePendingBindings()
        }
    }

}