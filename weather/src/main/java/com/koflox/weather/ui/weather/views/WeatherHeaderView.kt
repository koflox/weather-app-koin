package com.koflox.weather.ui.weather.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun WeatherHeaderView(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
        )
        Divider(
            color = Color.White,
            modifier = Modifier
                .height(1.dp)
                .padding(start = 16.dp)
                .alpha(0.5F),
        )
    }
}

@Preview
@Composable
internal fun WeatherHeaderViewPreview() {
    WeatherHeaderView(title = "Title")
}
