package com.ittinder.ittinder.service

import com.ittinder.ittinder.data.LoginObject
import com.ittinder.ittinder.data.LoginResponse
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.squareup.moshi.Json
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

interface UserApiService {
    @POST("/user")
    //TODO test this function!!!
    suspend fun createUser(): User

    @GET("/user")
    suspend fun getUser(@Header("Cookie") session_id: String): User

    @PUT("/user/update")
    suspend fun updateUser(@Header("Cookie") session_id: String, @Body user: User): String

    @POST("/user/login")
    suspend fun loginUser(@Body loginObject: LoginObject): LoginResponse
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