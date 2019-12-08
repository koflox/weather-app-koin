package com.example.weather_app.ui.current_weather

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.*
import com.example.weather_app.extensions.toView

class WeatherAdapter : RecyclerView.Adapter<BaseWeatherViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherViewHolder = when (viewType) {
        WeatherData.MAIN -> {
            val layoutId = R.layout.item_weather_data_main
            MainWeatherDataVH(toView(layoutId, parent))
        }
        WeatherData.HOURLY -> {
            val layoutId = R.layout.item_weather_data_hourly
            HourlyWeatherDataVH(toView(layoutId, parent))
        }
        WeatherData.DETAILS -> {
            val layoutId = R.layout.item_weather_data_details
            DetailsWeatherDataVH(toView(layoutId, parent))
        }
        WeatherData.PRECIPITATION -> {
            val layoutId = R.layout.item_weather_data_precipitation
            PrecipitationWeatherDataVH(toView(layoutId, parent))
        }
        WeatherData.FORECAST -> {
            val layoutId = R.layout.item_weather_data_forecast
            ForecastWeatherDataVH(toView(layoutId, parent))
        }
        else -> throw IllegalArgumentException("Unsupported weather data type!")
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderWeather: BaseWeatherViewHolder, position: Int) {
        holderWeather.bind(data[position])
    }

}

abstract class BaseWeatherViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bind(data: WeatherData)

}

class MainWeatherDataVH(v: View) : BaseWeatherViewHolder(v) {

    override fun bind(data: WeatherData) {
        val mainWeatherData = data as MainWeatherData
    }

}

class HourlyWeatherDataVH(v: View) : BaseWeatherViewHolder(v) {

    override fun bind(data: WeatherData) {
        val hourlyWeatherData = data as HourlyWeatherData
    }

}

class DetailsWeatherDataVH(v: View) : BaseWeatherViewHolder(v) {

    override fun bind(data: WeatherData) {
        val detailsWeatherData = data as DetailsWeatherData
    }

}

class PrecipitationWeatherDataVH(v: View) : BaseWeatherViewHolder(v) {

    override fun bind(data: WeatherData) {
        val precipitationData = data as PrecipitationWeatherData
    }

}

class ForecastWeatherDataVH(v: View) : BaseWeatherViewHolder(v) {

    override fun bind(data: WeatherData) {
        val forecastData = data as ForecastWeatherData
    }

}