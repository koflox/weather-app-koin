package com.koflox.weather.ui.weather

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.koflox.common_ui.base.BaseComposeFragment
import com.koflox.common_ui.setSupportActionBarSubtitle
import com.koflox.common_ui.setSupportActionBarTitle
import com.koflox.weather.R
import com.koflox.weather.ui.weather.displayed.DetailsWeatherUiModel
import com.koflox.weather.ui.weather.displayed.ForecastWeatherUiModel
import com.koflox.weather.ui.weather.displayed.HourlyWeatherUiModel
import com.koflox.weather.ui.weather.displayed.MainWeatherUiModel
import com.koflox.weather.ui.weather.displayed.PrecipitationWeatherUiModel
import com.koflox.weather.ui.weather.displayed.WeatherUiModel
import com.koflox.weather.ui.weather.displayed.WeatherUiState
import com.koflox.weather.ui.weather.views.DetailsWeatherUi
import com.koflox.weather.ui.weather.views.ForecastWeatherUi
import com.koflox.weather.ui.weather.views.HourlyWeatherUi
import com.koflox.weather.ui.weather.views.MainWeatherUi
import com.koflox.weather.ui.weather.views.PrecipitationWeatherUi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeatherFragment : BaseComposeFragment() {

    private val args by navArgs<WeatherFragmentArgs>()

    private val viewModel: WeatherViewModel by viewModel {
        parametersOf(
            args.placeToSearch,
            args.cityIdToSearch,
        )
    }

    override fun initViews() {
        setHasOptionsMenu(true)
        view.setBackgroundColor(resources.getColor(com.koflox.common_android_res.R.color.colorPrimary, null))
    }

    override fun addViewObservers() {
        viewLifecycleOwner.lifecycleScope.run {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.weatherUiState.collect(::renderUiState)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val state = viewModel.weatherUiState.value
        if (state !is WeatherUiState.Data) return
        if (state.isFavoriteCityOptionVisible.not()) return
        val menuRes = if (state.isFavoriteCity) R.menu.menu_city_is_added else R.menu.menu_city_is_not_added
        inflater.inflate(menuRes, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_remove_city,
            R.id.action_add_city -> {
                viewModel.onAddDeleteOptionClick()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun renderUiState(state: WeatherUiState) {
        when (state) {
            is WeatherUiState.Data -> {
                setSupportActionBarTitle(state.title)
                setSupportActionBarSubtitle(state.subtitle)
                requireActivity().invalidateOptionsMenu()
                view.setContent {
                    WeatherDataUi(weatherUiModels = state.weatherUiModels)
                }
            }

            is WeatherUiState.Loading -> {
                setSupportActionBarTitle(state.titleResId)
                view.setContent {
                    WeatherLoadingUi()
                }
            }

            is WeatherUiState.Error -> {
                setSupportActionBarTitle(state.titleResId)
                setSupportActionBarSubtitle(null)
                view.setContent {
                    WeatherErrorUi()
                }
            }
        }
    }

    @Composable
    private fun WeatherLoadingUi() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(40.dp)
            )
        }
    }

    @Composable
    private fun WeatherDataUi(weatherUiModels: List<WeatherUiModel>) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            weatherUiModels.forEach { model ->
                when (model) {
                    is DetailsWeatherUiModel -> {
                        item {
                            DetailsWeatherUi(model)
                        }
                    }

                    is ForecastWeatherUiModel -> {
                        item {
                            ForecastWeatherUi(model)
                        }
                    }

                    is HourlyWeatherUiModel -> {
                        item {
                            HourlyWeatherUi(model)
                        }
                    }

                    is MainWeatherUiModel -> {
                        item {
                            MainWeatherUi(model)
                        }
                    }

                    is PrecipitationWeatherUiModel -> {
                        item {
                            PrecipitationWeatherUi(model)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun WeatherErrorUi() {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentHeight(),
            textAlign = TextAlign.Center,
            color = Color.White,
            text = resources.getString(R.string.placeholder_text_weather),
        )
    }

}
