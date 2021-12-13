package com.example.healthapi

import com.example.healthapi.model.Healthid
import com.example.healthapi.model.Mobileno
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiClient {
    @Headers("Content-Type: application/json")
    @POST("/mobileno")
    fun addMobileNo(@Body mobileno: Mobileno): Call<Mobileno>

    @Headers("Content-Type: application/json")
    @POST("/healthid")
    fun createHealthId(@Body heathid: Healthid): Call<Healthid>

    @GET("healthid")
    fun getHealthIdData(): Call<List<Healthid>>

}