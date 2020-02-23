package com.example.weather_app.ui.current_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.MainWeatherData
import com.example.weather_app.databinding.ItemWeatherDataForecastItemBinding
import com.example.weather_app.ui.base.BindableAdapter

class ForecastWeatherDataAdapter(
    private val viewModel: CurrentWeatherViewModel
) : RecyclerView.Adapter<ForecastWeatherDataItemVH>(), BindableAdapter<MainWeatherData> {

    private val data = mutableListOf<MainWeatherData>()

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

class ForecastWeatherDataItemVH(
    private val dataBinding: ItemWeatherDataForecastItemBinding
) : RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(viewModel: CurrentWeatherViewModel, item: MainWeatherData) {
        dataBinding.apply {
            this.viewModel = viewModel
            this.item = item
            executePendingBindings()
        }
    }

}