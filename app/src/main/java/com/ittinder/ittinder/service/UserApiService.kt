package com.ittinder.ittinder.service

import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

private const val BASE_URL = "http://10.0.2.2:8080"


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
    suspend fun createUser(): User

    @GET("/user")
    suspend fun getUser(@Header("Cookie") session_id: String): User

    @PUT("/user/update")
    //TODO test this function!!!
    suspend fun updateUser(): User

    @POST("/user/login")
    //TODO test this function!!!
    suspend fun loginUser(): User
}

object UserApi {
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}

interface RandomUserStreamApiService {
    @GET("/user/stream")
    suspend fun getUsers(@Header("Cookie") session_id: String): List<RandomUserStream>
}

object RandomUserApi {
    val retrofitService: RandomUserStreamApiService by lazy {
        retrofit.create(RandomUserStreamApiService::class.java)
    }
}