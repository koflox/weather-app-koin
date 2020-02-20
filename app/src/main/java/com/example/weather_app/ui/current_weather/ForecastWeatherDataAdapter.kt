package com.example.weather_app.ui.current_weather

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.MainWeatherData
import com.example.weather_app.util.loadFromUrl
import com.example.weather_app.util.toView
import kotlinx.android.synthetic.main.item_weather_data_forecast_item.view.*

class ForecastWeatherDataAdapter : RecyclerView.Adapter<ForecastWeatherDataItemVH>() {

    private val data = mutableListOf<MainWeatherData>()

    fun setData(data: List<MainWeatherData>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherDataItemVH {
        return ForecastWeatherDataItemVH(toView(R.layout.item_weather_data_forecast_item, parent))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ForecastWeatherDataItemVH, position: Int) {
        holder.bind(data[position])
    }

}

class ForecastWeatherDataItemVH(private val v: View) : RecyclerView.ViewHolder(v) {

    @SuppressLint("SetTextI18n")
    fun bind(item: MainWeatherData) {
        v.run {
            tvDayName.text = item.dayName
            ivWeatherIcon.loadFromUrl(item.weatherIconUrl, R.drawable.ic_na)
            tvTempMax.text = "${item.tempMax} ${item.tempUnitMain}"
            tvTempMin.text = "${item.tempMin} ${item.tempUnitMain}"
        }
    }

}