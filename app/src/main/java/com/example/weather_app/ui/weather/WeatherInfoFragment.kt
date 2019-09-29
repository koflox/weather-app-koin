package com.example.weather_app.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.example.weather_app.R
import com.example.weather_app.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_weather_info.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherInfoFragment : BaseFragment() {

    private val viewModel by sharedViewModel<WeatherViewModel>()

    override fun getLayoutId() = R.layout.fragment_weather_info

    override fun afterViewCreated(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        ivIsCityFavorite.setOnClickListener {
            viewModel.checkFavoriteCity()
        }
    }

    override fun addObservers() {
        viewModel.run {
            weatherInfo.observe(viewLifecycleOwner, Observer { weatherInfo ->
                weatherInfo?.run {
                    tvCityName.text = cityName
                    tvDate.text = date
                    tvWeatherDescription.text = weatherDescription
                    tvTemperature.text = temperature
                    tvWindSpeed.text = windSpeed
                    tvPressure.text = pressure
                    tvHumidity.text = humidity
                    with(resources) {
                        ivIsCityFavorite.background = if (isFavoriteCity)
                            getDrawable(R.drawable.ic_city_favorite, null)
                        else
                            getDrawable(R.drawable.ic_city_not_favorite, null)
                    }
                    Log.d("Logos", "isFavoriteCity $isFavoriteCity")
                }
            })

            loading.observe(viewLifecycleOwner, Observer { isLoading ->
                groupWeatherDescriptionViews.visibility = if (isLoading) GONE else VISIBLE
                pbWeatherLoading.visibility = if (isLoading) VISIBLE else GONE
            })
        }
    }

}
