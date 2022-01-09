package com.ittinder.ittinder.service

import com.ittinder.ittinder.data.Image
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "http://10.0.2.2:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object ImageApi {
    val retrofitService: ImageApiService by lazy {
        retrofit.create(ImageApiService::class.java)
    }
}

interface ImageApiService {
    @Multipart
    @POST("/user/image")
    //TODO test this function!!!
    suspend fun uploadImage(
        @Header("Cookie") session_id: String,
        @Body multipart: Multipart): Image
}


