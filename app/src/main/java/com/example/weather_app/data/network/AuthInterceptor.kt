package com.example.weather_app.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val urlBuilder = request.url.newBuilder().apply {
            setEncodedQueryParameter("key", apiKey)
        }
        builder.url(urlBuilder.build())
        return chain.proceed(builder.build())
    }

}