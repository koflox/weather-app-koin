package com.koflox.weather.ui.weather.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.koflox.weather.ui.weather.displayed.MainWeatherUiModel

@Composable
internal fun MainWeatherUi(model: MainWeatherUiModel) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val (textWeatherDesc, textTemp, imageWeatherIcon, columnTempRange) = createRefs()
        val midVerticalGuideline = createGuidelineFromStart(0.5F)
        Text(
            text = model.weatherDescription,
            modifier = Modifier
                .constrainAs(textWeatherDesc) {
                    top.linkTo(parent.top)
                    end.linkTo(midVerticalGuideline)
                }
                .padding(end = 2.dp),
            color = Color.White,
            fontSize = 18.sp,
        )
        Text(
            text = model.temp.toString(),
            modifier = Modifier
                .constrainAs(textTemp) {
                    top.linkTo(textWeatherDesc.bottom)
                    end.linkTo(midVerticalGuideline)
                }
                .padding(end = 2.dp),
            color = Color.White,
            fontSize = 60.sp,
        )
        AsyncImage(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .padding(start = 2.dp)
                .constrainAs(imageWeatherIcon) {
                    top.linkTo(textWeatherDesc.top)
                    bottom.linkTo(textWeatherDesc.bottom)
                    start.linkTo(midVerticalGuideline)
                },
            model = model.weatherIconUrl,
            contentDescription = null,
        )
        MinMaxTempUi(
            modifier = Modifier
                .constrainAs(columnTempRange) {
                    top.linkTo(textTemp.top)
                    bottom.linkTo(textTemp.bottom)
                    start.linkTo(textTemp.end)
                }
                .padding(start = 2.dp),
            minTemp = "${model.tempMin} ${model.tempUnitMain}",
            maxTemp = "${model.tempMax} ${model.tempUnitMain}",
        )
    }
}

@Preview
@Composable
internal fun MainWeatherViewPreview() {
    MainWeatherUi(
        model = MainWeatherUiModel(
            temp = 15,
            tempMin = 9,
            tempMax = 17,
            tempUnitMain = "°C",
            tempUnitExtra = "",
            weatherIconUrl = "https://openweathermap.org/img/wn/02d.png",
            weatherDescription = "Clouds",
            dayName = "",
            chanceOfPrecipitation = "",
        )
    )
}
