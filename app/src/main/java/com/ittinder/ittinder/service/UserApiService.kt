package com.ittinder.ittinder.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

private const val BASE_URL = "http://localhost:8080"
//    "http://10.0.2.2:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    @POST("/user")
    //TODO test this function!!!
    suspend fun createUser(): String

    @GET("/user/{id}")
    //TODO test this function!!!
    suspend fun getUser(): String

    @PUT("/user/update")
    //TODO test this function!!!
    suspend fun updateUser(): String
}

object UserApi {
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}


