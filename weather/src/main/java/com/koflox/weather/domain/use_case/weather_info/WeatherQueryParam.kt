package com.koflox.weather.domain.use_case.weather_info

sealed class WeatherQueryParam(val value: String) {
    class Place(name: String) : WeatherQueryParam(name)
    class City(id: String) : WeatherQueryParam(id)
}
