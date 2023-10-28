package com.koflox.weather.ui.weather.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.koflox.common_android_res.colorPrimaryDark
import com.koflox.weather.ui.weather.displayed.DetailsWeatherUiModel

@Composable
internal fun DetailsWeatherUi(
    model: DetailsWeatherUiModel,
) {
    WeatherHeaderView(
        title = model.sectionTitle,
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 4.dp,
        ),
    )
    @OptIn(ExperimentalLayoutApi::class)
    (FlowRow(
        maxItemsInEachRow = 4,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
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
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(
                        id = it.resourceId,
                    ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                )
                Text(
                    text = it.desc,
                    color = Color.White,
                    fontSize = 12.sp,
                )
                Text(
                    text = "${it.value} ${it.unit}",
                    color = Color.White,
                    fontSize = 12.sp,
                )
            }
        }
    })
}
