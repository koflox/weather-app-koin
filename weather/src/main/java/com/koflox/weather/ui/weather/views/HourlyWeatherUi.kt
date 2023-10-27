package com.koflox.weather.ui.weather.views

import android.graphics.Color
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.koflox.weather.ui.view.custom.GraphicView
import com.koflox.weather.ui.weather.displayed.HourlyWeatherUiModel

@Composable
internal fun HourlyWeatherUi(
    model: HourlyWeatherUiModel,
) {
    WeatherHeaderView(title = model.sectionTitle)
    AndroidView(
        modifier = Modifier.aspectRatio(4F),
        factory = { context ->
            GraphicView(context)
        },
        update = {
            it.dataType = GraphicView.DataType.HOURLY_TEMP
            it.colorData = Color.WHITE
            it.colorGraphic = Color.WHITE
            it.setData(model.values)
        },
    )
}
