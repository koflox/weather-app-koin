package com.koflox.weather.ui.weather.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun MinMaxTempUi(
    modifier: Modifier = Modifier,
    minTemp: String,
    maxTemp: String,
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = minTemp,
            color = Color.White,
            fontSize = 14.sp,
        )
        Divider(
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .alpha(0.5F),
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            text = maxTemp,
            color = Color.White,
            fontSize = 14.sp,
        )
    }
}

