package com.koflox.weather.ui.weather.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.koflox.common_android_res.colorPrimaryDark
import com.koflox.weather.ui.weather.displayed.ForecastWeatherUiModel

@Composable
internal fun ForecastWeatherUi(
    model: ForecastWeatherUiModel,
) {
    WeatherHeaderView(
        title = model.sectionTitle,
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 4.dp,
        ),
    )
    @OptIn(ExperimentalLayoutApi::class)
    FlowRow(
        maxItemsInEachRow = model.values.size,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        model.values.forEach {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorPrimaryDark)
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = it.dayName,
                    color = Color.White,
                    fontSize = 12.sp,
                )
                AsyncImage(
                    modifier = Modifier
                        .width(34.dp)
                        .height(34.dp)
                        .padding(start = 2.dp),
                    model = it.weatherIconUrl,
                    contentDescription = null,
                )
                MinMaxTempUi(
                    minTemp = "${it.tempMin} ${it.tempUnitMain}",
                    maxTemp = "${it.tempMax} ${it.tempUnitMain}",
                )
            }
        }
    }
}
