package com.koflox.weather.ui.displayed

import com.koflox.common_jvm_util.formatToLocalTime
import com.koflox.weather.R
import com.koflox.weather.domain.entity.CurrentWeather
import com.koflox.weather.domain.entity.SystemOfMeasurement
import java.util.*
import com.koflox.common_android_res.R as commonResR

internal fun CurrentWeather.toMainWeatherData(timePattern: String, unit: SystemOfMeasurement): MainWeatherData {
    val weather = weather.firstOrNull()
    val tempUnitMain = when (unit) {
        SystemOfMeasurement.METRIC -> "°C"
        SystemOfMeasurement.IMPERIAL -> "F"
    }
    val tempUnitExtra = when (unit) {
        SystemOfMeasurement.METRIC -> "°"
        SystemOfMeasurement.IMPERIAL -> "F"
    }
    return MainWeatherData(
        main.temp.toInt(),
        main.tempMin.toInt(),
        main.tempMax.toInt(),
        tempUnitMain,
        tempUnitExtra,
        weather?.weatherIconUrl,
        weather?.main ?: "",
        dt.formatToLocalTime(timePattern, timezone)
            .lowercase(Locale.getDefault())
    )
}

internal fun CurrentWeather.toDetailsWeatherData(sectionTitle: String, unit: SystemOfMeasurement): DetailsWeatherData {
    val windSpeedUnit = when (unit) {
        SystemOfMeasurement.METRIC -> "m/s"
        SystemOfMeasurement.IMPERIAL -> "m/h"
    }
    val values = mutableListOf<DetailsWeatherDataItem>().apply {
        add(DetailsWeatherDataItem(R.drawable.ic_thermometer, "Feels like", "N/A"))
        add(DetailsWeatherDataItem(R.drawable.ic_wind, "Wind", wind.speed.toInt().toString(), windSpeedUnit))
        add(DetailsWeatherDataItem(R.drawable.ic_humidity, "Humidity", main.humidity.toString(), "%"))
        add(DetailsWeatherDataItem(R.drawable.ic_barometer, "Pressure", main.pressure.toString(), "hPa"))
        add(DetailsWeatherDataItem(commonResR.drawable.ic_na, "Visibility", "N/A"))
        add(DetailsWeatherDataItem(commonResR.drawable.ic_na, "Dew point", "N/A"))
    }
    return DetailsWeatherData(sectionTitle, values)
}
