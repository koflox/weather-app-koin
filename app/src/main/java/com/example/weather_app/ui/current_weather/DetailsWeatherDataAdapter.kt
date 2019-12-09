package com.example.weather_app.ui.current_weather

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.util.toView
import kotlinx.android.synthetic.main.item_weather_data_details_item.view.*

class DetailsWeatherDataAdapter : RecyclerView.Adapter<DetailsWeatherDataItemVH>() {

    private val data = mutableListOf<Pair<Int, String>>()

    fun setData(data: List<Pair<Int, String>>) {
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

    fun bind(item: Pair<Int, String>) {
        v.run {
            if (item.first != 0) //todo remove "if" statement after adding missing icons
                ivDetailIcon.setImageResource(item.first)
            tvDescription.text = item.second
        }
    }

}