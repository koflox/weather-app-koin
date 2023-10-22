package com.koflox.weather.ui.weather

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koflox.common_ui.base.UniversalItemDecorator
import com.koflox.weather.R
import com.koflox.weather.databinding.ItemWeatherDataDetailsBinding
import com.koflox.weather.databinding.ItemWeatherDataForecastBinding
import com.koflox.weather.databinding.ItemWeatherDataHourlyBinding
import com.koflox.weather.databinding.ItemWeatherDataMainBinding
import com.koflox.weather.databinding.ItemWeatherDataPrecipitationBinding
import com.koflox.weather.ui.displayed.DetailsWeatherData
import com.koflox.weather.ui.displayed.ForecastWeatherData
import com.koflox.weather.ui.displayed.HourlyWeatherData
import com.koflox.weather.ui.displayed.MainWeatherData
import com.koflox.weather.ui.displayed.PrecipitationWeatherData
import com.koflox.weather.ui.displayed.WeatherData
import com.koflox.weather.ui.weather.current.DetailsWeatherDataAdapter
import com.koflox.weather.ui.weather.forecast.ForecastWeatherDataAdapter
import com.koflox.common_android_res.R as commonResR

internal abstract class BaseWeatherDataVH(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bind(viewModel: WeatherViewModel, data: WeatherData)

}

internal class MainWeatherDataVH(private val dataBinding: ItemWeatherDataMainBinding) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        dataBinding.apply {
            this.viewModel = viewModel
            item = data as MainWeatherData
            executePendingBindings()
        }
    }

}

internal class HourlyWeatherDataVH(
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

internal class DetailsWeatherDataVH(
    private val dataBinding: ItemWeatherDataDetailsBinding,
    private val adapterDetailsWeatherData: DetailsWeatherDataAdapter
) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        dataBinding.rvDetailsWeatherData.apply {
            val spacing = context.resources.getDimensionPixelSize(commonResR.dimen.common_list_spacing)
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

internal class PrecipitationWeatherDataVH(
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

internal class ForecastWeatherDataVH(
    private val dataBinding: ItemWeatherDataForecastBinding,
    private val adapterForecastWeatherData: ForecastWeatherDataAdapter
) : BaseWeatherDataVH(dataBinding.root) {

    override fun bind(viewModel: WeatherViewModel, data: WeatherData) {
        data as ForecastWeatherData
        dataBinding.rvForecastsWeatherData.apply {
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
