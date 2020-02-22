package com.example.weather_app.ui.current_weather

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.WeatherData
import com.example.weather_app.util.toView

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
        WeatherData.MAIN -> MainWeatherDataVH(
            parent,
            toView(R.layout.item_weather_data_main, parent)
        )
        WeatherData.HOURLY -> HourlyWeatherDataVH(
            parent,
            toView(R.layout.item_weather_data_hourly, parent)
        )
        WeatherData.DETAILS -> DetailsWeatherDataVH(
            toView(R.layout.item_weather_data_details, parent)
        )
        WeatherData.PRECIPITATION -> PrecipitationWeatherDataVH(
            parent,
            toView(R.layout.item_weather_data_precipitation, parent)
        )
        WeatherData.FORECAST -> ForecastWeatherDataVH(
            toView(R.layout.item_weather_data_forecast, parent)
        )
        else -> throw IllegalArgumentException("Unsupported weather data type: $viewType")
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderWeather: BaseWeatherDataVH, position: Int) {
        holderWeather.bind(data[position])
    }

}