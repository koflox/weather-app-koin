package com.example.weather_app.ui.current_weather

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.DetailsWeatherDataItem
import com.example.weather_app.ui.base.BindableAdapter
import com.example.weather_app.util.toSpannableString
import com.example.weather_app.util.toView
import kotlinx.android.synthetic.main.item_weather_data_details_item.view.*

class DetailsWeatherDataAdapter : RecyclerView.Adapter<DetailsWeatherDataItemVH>(), BindableAdapter<DetailsWeatherDataItem> {

    private val data = mutableListOf<DetailsWeatherDataItem>()

    override fun setData(data: List<DetailsWeatherDataItem>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsWeatherDataItemVH {
        return DetailsWeatherDataItemVH(toView(R.layout.item_weather_data_details_item, parent))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DetailsWeatherDataItemVH, position: Int) {
        holder.bind(data[position])
    }

}

class DetailsWeatherDataItemVH(private val v: View) : RecyclerView.ViewHolder(v) {

    fun bind(item: DetailsWeatherDataItem) {
        v.run {
            if (item.resourceId != 0) //todo remove "if" statement after adding missing icons
                ivDetailIcon.setImageResource(item.resourceId)

            val value = "${item.value} ${item.unit}"
            val text = "${item.desc}\n$value"
            tvDescription.text = text.toSpannableString(
                    color = Color.rgb(245, 240, 240),
                    sizeProportion = 1.15F,
                    universalStart = text.length - value.length,
                    universalEnd = text.length,
                    useUniversalRange = true
            )
        }
    }

}