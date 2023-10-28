package com.koflox.cities.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.koflox.cities.CitiesNavigator
import com.koflox.cities.R
import com.koflox.common_android_res.colorPrimary
import com.koflox.common_android_res.colorWhite
import com.koflox.common_ui.base.BaseComposeFragment
import com.koflox.common_ui.setSupportActionBarSubtitle
import com.koflox.common_ui.setSupportActionBarTitle
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CitiesFragment : BaseComposeFragment() {

    private val viewModel by viewModel<CitiesViewModel>()
    private val citiesNavigator: CitiesNavigator by inject()

    override fun addViewObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.citiesUiState.collect(::renderUiState)
            }
        }
    }

    private fun renderUiState(state: CitiesUiState) {
        when (state) {
            is CitiesUiState.Data -> {
                setSupportActionBarTitle(state.titleResId)
                setSupportActionBarSubtitle(null)
                view.setContent {
                    CitiesUi(
                        models = state.models,
                        onCityClick = ::navigateToWeather,
                    )
                }
            }

            is CitiesUiState.Loading -> {
                setSupportActionBarTitle(state.titleResId)
                setSupportActionBarSubtitle(null)
                view.setContent {
                    CitiesLoadingUi()
                }
            }
        }
    }

    @Composable
    private fun CitiesUi(
        models: List<CityUiModel>,
        onCityClick: (String) -> Unit,
    ) {
        if (models.isEmpty()) {
            Text(text = resources.getString(R.string.placeholder_text_favorite_cities))
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                models.forEach { model ->
                    item {
                        ClickableText(
                            onClick = { onCityClick.invoke(model.id) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colorPrimary)
                                .padding(8.dp),
                            text = AnnotatedString(
                                text = model.displayedName,
                                spanStyle = SpanStyle(color = colorWhite),
                            ),
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CitiesLoadingUi() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(40.dp)
            )
        }
    }

    private fun navigateToWeather(cityId: String) {
        citiesNavigator.goToWeatherDetails(
            fragment = this,
            placeName = null,
            cityId = cityId,
        )
    }

}
