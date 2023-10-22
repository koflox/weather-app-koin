package com.koflox.weather.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val apiKeyParam: String,
    private val apiKeyValue: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val urlBuilder = request.url.newBuilder().apply {
            setEncodedQueryParameter(apiKeyParam, apiKeyValue)
        }
        builder.url(urlBuilder.build())
        return chain.proceed(builder.build())
    }

}
