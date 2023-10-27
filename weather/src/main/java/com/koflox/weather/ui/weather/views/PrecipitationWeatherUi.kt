package com.koflox.weather.ui.weather.views

import android.graphics.Color
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.koflox.weather.ui.view.custom.GraphicView
import com.koflox.weather.ui.weather.displayed.PrecipitationWeatherUiModel

@Composable
internal fun PrecipitationWeatherUi(
    model: PrecipitationWeatherUiModel,
) {
    WeatherHeaderView(
        title = model.sectionTitle,
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 4.dp,
        ),
    )
    AndroidView(
        modifier = Modifier.aspectRatio(4F),
        factory = { context ->
            GraphicView(context)
        },
        update = { graphicView ->
            graphicView.apply {
                dataType = GraphicView.DataType.PRECIPITATION
                colorData = Color.WHITE
                colorGraphic = Color.WHITE
                setData(model.values)
            }
        },
    )
}
