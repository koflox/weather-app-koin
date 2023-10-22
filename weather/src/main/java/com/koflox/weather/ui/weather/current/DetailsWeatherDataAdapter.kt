package com.koflox.weather.ui.weather.current

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.koflox.common_ui.base.BindableAdapter
import com.koflox.common_ui.toSpannableString
import com.koflox.weather.R
import com.koflox.weather.databinding.ItemWeatherDataDetailsItemBinding
import com.koflox.weather.ui.displayed.DetailsWeatherDataItem
import com.koflox.weather.ui.weather.WeatherViewModel

internal class DetailsWeatherDataAdapter(
    private val viewModel: WeatherViewModel
) : RecyclerView.Adapter<DetailsWeatherDataItemVH>(), BindableAdapter<DetailsWeatherDataItem> {

    private val data = mutableListOf<DetailsWeatherDataItem>()

    @SuppressLint("NotifyDataSetChanged")
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

internal class DetailsWeatherDataItemVH(
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
