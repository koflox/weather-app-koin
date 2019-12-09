package com.example.weather_app.ui.current_weather

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.*
import com.example.weather_app.util.toView
import kotlinx.android.synthetic.main.item_weather_data_details.view.*

class WeatherAdapter : RecyclerView.Adapter<BaseWeatherDataVH>() {

    private val data = mutableListOf<WeatherData>()

    fun setData(data: List<WeatherData>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getDataType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherDataVH = when (viewType) {
        WeatherData.MAIN -> MainWeatherDataVH(parent, toView(R.layout.item_weather_data_main, parent))
        WeatherData.HOURLY -> HourlyWeatherDataVH(parent, toView(R.layout.item_weather_data_hourly, parent))
        WeatherData.DETAILS -> DetailsWeatherDataVH(toView(R.layout.item_weather_data_details, parent))
        WeatherData.PRECIPITATION -> PrecipitationWeatherDataVH(toView(R.layout.item_weather_data_precipitation, parent))
        WeatherData.FORECAST -> ForecastWeatherDataVH(toView(R.layout.item_weather_data_forecast, parent))
        else -> throw IllegalArgumentException("Unsupported weather data type!")
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderWeather: BaseWeatherDataVH, position: Int) {
        holderWeather.bind(data[position])
    }

}

abstract class BaseWeatherDataVH(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bind(data: WeatherData)

}

class MainWeatherDataVH(private val parent: View, private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val mainWeatherData = data as MainWeatherData

        val desiredHeight = parent.measuredHeight / 10 * 7
        when (v) {
            is ConstraintLayout -> v.minHeight = desiredHeight
            else -> v.minimumHeight = desiredHeight
        }
    }

}

class HourlyWeatherDataVH(private val parent: View, private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val hourlyWeatherData = data as HourlyWeatherData
        v.minimumHeight = parent.measuredHeight / 10 * 2
    }

}

class DetailsWeatherDataVH(private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val detailsWeatherData = data as DetailsWeatherData
        v.rvDetailsWeatherData.apply {
            val spacing = context.resources.getDimensionPixelSize(R.dimen.common_grid_spacing)
            val spanCount = context.resources.getInteger(R.integer.span_count_details_weather_data)

            addItemDecoration(ItemDecorator(spacing, spanCount))
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = DetailsWeatherDataAdapter().apply {
                setData(detailsWeatherData.values)
            }
        }
    }

}

class PrecipitationWeatherDataVH(v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val precipitationData = data as PrecipitationWeatherData
    }

}

class ForecastWeatherDataVH(v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val forecastData = data as ForecastWeatherData
    }

}