package com.example.weather_app.ui.weather

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.DetailsWeatherDataItem
import com.example.weather_app.databinding.ItemWeatherDataDetailsItemBinding
import com.example.weather_app.ui.base.BindableAdapter
import com.example.weather_app.util.toSpannableString

class DetailsWeatherDataAdapter(
    private val viewModel: WeatherViewModel
) : RecyclerView.Adapter<DetailsWeatherDataItemVH>(), BindableAdapter<DetailsWeatherDataItem> {

    private val data = mutableListOf<DetailsWeatherDataItem>()

    override fun setData(data: List<DetailsWeatherDataItem>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsWeatherDataItemVH {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = DataBindingUtil.inflate<ItemWeatherDataDetailsItemBinding>(
            inflater, R.layout.item_weather_data_details_item,
            parent, false
        )
        return DetailsWeatherDataItemVH(dataBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DetailsWeatherDataItemVH, position: Int) {
        holder.bind(viewModel, data[position])
    }

}

class DetailsWeatherDataItemVH(
    private val dataBinding: ItemWeatherDataDetailsItemBinding
) : RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(viewModel: WeatherViewModel, item: DetailsWeatherDataItem) {
        dataBinding.apply {
            this.viewModel = viewModel
            this.item = item
            executePendingBindings()
        }
        val value = "${item.value} ${item.unit}"
        val text = "${item.desc}\n$value"
        dataBinding.tvDescription.text = text.toSpannableString(
            color = Color.rgb(245, 240, 240),
            sizeProportion = 1.15F,
            universalStart = text.length - value.length,
            universalEnd = text.length,
            useUniversalRange = true
        )
    }

}