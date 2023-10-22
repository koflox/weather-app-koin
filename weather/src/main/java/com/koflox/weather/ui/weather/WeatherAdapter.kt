package com.koflox.weather.ui.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.koflox.common_ui.base.BindableAdapter
import com.koflox.weather.R
import com.koflox.weather.ui.displayed.WeatherData
import com.koflox.weather.ui.weather.current.DetailsWeatherDataAdapter
import com.koflox.weather.ui.weather.forecast.ForecastWeatherDataAdapter

internal class WeatherAdapter(
    private val viewModel: WeatherViewModel
) : RecyclerView.Adapter<BaseWeatherDataVH>(), BindableAdapter<WeatherData> {

    private val data = mutableListOf<WeatherData>()

    private val adapterDetailsWeatherData = DetailsWeatherDataAdapter(viewModel)
    private val adapterForecastWeatherData = ForecastWeatherDataAdapter(viewModel)

    @SuppressLint("NotifyDataSetChanged")
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
