package com.example.weather_app

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.koflox.cities.CitiesNavigator

class CitiesNavigatorImpl : CitiesNavigator {
    override fun goToWeatherDetails(
        fragment: Fragment,
        placeName: String,
        cityId: Int,
    ) {
        fragment.findNavController()
            .navigate(
                R.id.currentWeatherFragment,
                bundleOf(
                    "place_to_search" to placeName,
                    "city_id_to_search" to cityId,
                )
            )
    }

}
