package com.koflox.cities.cities

import androidx.annotation.StringRes

internal sealed class CitiesUiState {
    data class Data(
        @StringRes
        val titleResId: Int,
        val models: List<CityUiModel>,
    ) : CitiesUiState()

    data class Loading(
        @StringRes
        val titleResId: Int,
    ) : CitiesUiState()
}
