package com.example.weather_app.extensions

inline fun <R, A, B> nonNull(a: A?, b: B?, block: (A, B) -> R): R? {
    return if (a != null && b != null) {
        block(a, b)
    } else null
}