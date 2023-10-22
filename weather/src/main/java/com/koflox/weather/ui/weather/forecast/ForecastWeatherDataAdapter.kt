package com.koflox.weather.ui.weather.forecast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.koflox.common_ui.base.BindableAdapter
import com.koflox.weather.R
import com.koflox.weather.databinding.ItemWeatherDataForecastItemBinding
import com.koflox.weather.ui.displayed.MainWeatherData
import com.koflox.weather.ui.weather.WeatherViewModel

internal class ForecastWeatherDataAdapter(
    private val viewModel: WeatherViewModel
) : RecyclerView.Adapter<ForecastWeatherDataItemVH>(), BindableAdapter<MainWeatherData> {

    private val data = mutableListOf<MainWeatherData>()

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: List<MainWeatherData>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherDataItemVH {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = DataBindingUtil.inflate<ItemWeatherDataForecastItemBinding>(
            inflater, R.layout.item_weather_data_forecast_item,
            parent, false
        )
        return ForecastWeatherDataItemVH(dataBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ForecastWeatherDataItemVH, position: Int) {
        holder.bind(viewModel, data[position])
    }

}

internal class ForecastWeatherDataItemVH(
    private val dataBinding: ItemWeatherDataForecastItemBinding
) : RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(viewModel: WeatherViewModel, item: MainWeatherData) {
        dataBinding.apply {
            this.viewModel = viewModel
            this.item = item
            executePendingBindings()
        }
    }

}
