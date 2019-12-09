package com.example.weather_app.ui.current_weather

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.*
import com.example.weather_app.util.toView

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
        WeatherData.MAIN -> MainWeatherDataVH(toView(R.layout.item_weather_data_main, parent))
        WeatherData.HOURLY -> HourlyWeatherDataVH(toView(R.layout.item_weather_data_hourly, parent))
        WeatherData.DETAILS -> DetailsWeatherDataVH(
            toView(
                R.layout.item_weather_data_details,
                parent
            )
        )
        WeatherData.PRECIPITATION -> PrecipitationWeatherDataVH(
            toView(
                R.layout.item_weather_data_precipitation,
                parent
            )
        )
        WeatherData.FORECAST -> ForecastWeatherDataVH(
            toView(
                R.layout.item_weather_data_forecast,
                parent
            )
        )
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