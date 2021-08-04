package com.jumia.myapplication.infrastructure

import com.jumia.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class OkHttpClientProvider {

    val provideOkHttpClient: OkHttpClient by lazy {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else OkHttpClient
            .Builder()
            .build()
    }

    companion object {
        private const val CONNECT_TIMEOUT_IN_MS = 30000
    }
}