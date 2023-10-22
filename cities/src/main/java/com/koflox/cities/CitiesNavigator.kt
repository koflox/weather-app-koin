package com.koflox.cities

import androidx.fragment.app.Fragment

interface CitiesNavigator {
    fun goToWeatherDetails(
        fragment: Fragment,
        placeName: String?,
        cityId: String?,
    )
}
