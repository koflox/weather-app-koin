package com.example.weather_app.util

inline fun <R : Any, T1 : Any, T2 : Any> notNull(a: T1?, b: T2?, block: (T1, T2) -> R): R? = when {
    a != null && b != null -> block(a, b)
    else -> null
}