package com.jumia.myapplication.infrastructure

import com.jumia.myapplication.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory(private val okHttpClient: OkHttpClient) {
    val apiService: JumiaWebService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.JUMIA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(JumiaWebService::class.java)
    }
}