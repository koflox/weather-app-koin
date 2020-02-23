package com.example.weather_app.ui.current_weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.WeatherData
import com.example.weather_app.ui.base.BindableAdapter

class WeatherAdapter(
    private val viewModel: CurrentWeatherViewModel
) : RecyclerView.Adapter<BaseWeatherDataVH>(), BindableAdapter<WeatherData> {

    private val data = mutableListOf<WeatherData>()

    private val adapterDetailsWeatherData = DetailsWeatherDataAdapter(viewModel)
    private val adapterForecastWeatherData = ForecastWeatherDataAdapter(viewModel)

    override fun setData(data: List<WeatherData>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getDataType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherDataVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            WeatherData.MAIN -> MainWeatherDataVH(
                DataBindingUtil.inflate(inflater, R.layout.item_weather_data_main, parent, false)
            )
            WeatherData.HOURLY -> HourlyWeatherDataVH(
                DataBindingUtil.inflate(inflater, R.layout.item_weather_data_hourly, parent, false),
                parent
            )
            WeatherData.DETAILS -> DetailsWeatherDataVH(
                DataBindingUtil.inflate(inflater, R.layout.item_weather_data_details, parent, false),
                adapterDetailsWeatherData
            )
            WeatherData.PRECIPITATION -> PrecipitationWeatherDataVH(
                DataBindingUtil.inflate(inflater, R.layout.item_weather_data_precipitation, parent, false),
                parent
            )
            WeatherData.FORECAST -> ForecastWeatherDataVH(
                DataBindingUtil.inflate(inflater, R.layout.item_weather_data_forecast, parent, false),
                adapterForecastWeatherData
            )
            else -> throw IllegalArgumentException("Unsupported weather data type: $viewType")
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holderWeather: BaseWeatherDataVH, position: Int) {
        holderWeather.bind(viewModel, data[position])
    }

}