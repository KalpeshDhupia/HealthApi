package com.example.healthapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    @Volatile
    private var apiClient: ApiClient? = null

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Synchronized
    fun getInstance(): ApiClient {
        if (apiClient == null) {
            apiClient = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:3000/")
                .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
                .build().create(ApiClient::class.java)
        }
        return apiClient!!
    }
}