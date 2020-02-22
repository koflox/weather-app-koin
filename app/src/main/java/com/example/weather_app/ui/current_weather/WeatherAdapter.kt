package com.example.weather_app.ui.current_weather

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.displayed.*
import com.example.weather_app.ui.base.UniversalItemDecorator
import com.example.weather_app.ui.view.GraphicView
import com.example.weather_app.util.loadFromUrl
import com.example.weather_app.util.toView
import kotlinx.android.synthetic.main.item_weather_data_details.view.*
import kotlinx.android.synthetic.main.item_weather_data_forecast.view.*
import kotlinx.android.synthetic.main.item_weather_data_hourly.view.*
import kotlinx.android.synthetic.main.item_weather_data_main.view.*
import kotlinx.android.synthetic.main.item_weather_data_precipitation.view.*

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

abstract class BaseWeatherDataVH(v: View) : RecyclerView.ViewHolder(v) {

    abstract fun bind(data: WeatherData)

}

class MainWeatherDataVH(private val parent: View, private val v: View) : BaseWeatherDataVH(v) {

    @SuppressLint("SetTextI18n")
    override fun bind(data: WeatherData) {
        val mainWeatherData = data as MainWeatherData
        v.run {
            tvTemp.text = when {
                mainWeatherData.temp > 0 -> "+${mainWeatherData.temp}"
                else -> mainWeatherData.temp.toString()
            }
            tvWeatherDesc.text = mainWeatherData.weatherDescription
            ivWeatherIcon.loadFromUrl(mainWeatherData.weatherIconUrl)
            tvTempMin.text = "${mainWeatherData.tempMin} ${mainWeatherData.tempUnitExtra}"
            tvTempMax.text = "${mainWeatherData.tempMax} ${mainWeatherData.tempUnitExtra}"
        }
    }

}

class HourlyWeatherDataVH(private val parent: View, private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val hourlyWeatherData = data as HourlyWeatherData
        v.minimumHeight = parent.measuredHeight / 10 * 2
        v.htvWeatherDataHourlyTitle.text = hourlyWeatherData.sectionTitle
        v.graphicViewHourlyData.dataType = GraphicView.DataType.HOURLY_TEMP
        v.graphicViewHourlyData.setData(hourlyWeatherData.values)
    }

}

class DetailsWeatherDataVH(private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val detailsWeatherData = data as DetailsWeatherData
        v.rvDetailsWeatherData.apply {
            val spacing = context.resources.getDimensionPixelSize(R.dimen.common_list_spacing)
            val spanCount = context.resources.getInteger(R.integer.span_count_details_weather_data)
            addItemDecoration(UniversalItemDecorator(spacing, UniversalItemDecorator.Type.GRID, spanCount))
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = DetailsWeatherDataAdapter().apply {
                setData(detailsWeatherData.values)
            }
        }
        v.htvWeatherDataDetailsTitle.text = detailsWeatherData.sectionTitle
    }

}

class PrecipitationWeatherDataVH(private val parent: View, private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val precipitationData = data as PrecipitationWeatherData
        v.minimumHeight = (parent.measuredHeight / 10 * 2.5F).toInt()
        v.graphicViewPrecipitationData.dataType = GraphicView.DataType.PRECIPITATION
        v.graphicViewPrecipitationData.setData(precipitationData.values)

        v.htvWeatherDataPrecipitationTitle.text = precipitationData.sectionTitle
    }

}

class ForecastWeatherDataVH(private val v: View) : BaseWeatherDataVH(v) {

    override fun bind(data: WeatherData) {
        val forecastData = data as ForecastWeatherData

        v.rvForecastsWeatherData.apply {
            layoutManager = GridLayoutManager(context, forecastData.values.size)
            adapter = ForecastWeatherDataAdapter().apply {
                setData(forecastData.values)
            }
        }

        v.htvWeatherDataForecastTitle.text = forecastData.sectionTitle
    }

}