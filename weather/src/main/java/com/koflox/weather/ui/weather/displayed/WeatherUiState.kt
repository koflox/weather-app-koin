package com.koflox.weather.ui.weather.displayed

import androidx.annotation.StringRes

internal sealed class WeatherUiState {
    data class Data(
        val weatherUiModels: List<WeatherUiModel>,
        val title: String,
        val subtitle: String,
        val isFavoriteCity: Boolean,
        val isFavoriteCityOptionVisible: Boolean,
    ) : WeatherUiState()

    data class Loading(
        @StringRes
        val titleResId: Int,
    ) : WeatherUiState()

    data class Error(
        @StringRes
        val titleResId: Int,
    ) : WeatherUiState()
}
