package com.ittinder.ittinder.Modules
import android.app.Service
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.Image
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "http://10.0.2.2:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    //.addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface SwipeLeftApiService {
    @POST("/SwipeLeft")
    suspend fun postItem(@Query("idUser1") idUser1: Int, @Query("idUser2") idUser2: Int)
}
object SwipeLeftApi {
    val retrofitService: SwipeLeftApiService by lazy {
        retrofit.create(SwipeLeftApiService::class.java)
    }
}

interface  SwipeRightApiService {
    @POST ("/SwipeRight")
    suspend fun postItem(@Query("idUser1") idUser1: Int, @Query("idUser2") idUser2: Int)
}

object SwipeRightApi {
    val retrofitService: SwipeRightApiService by lazy {
        retrofit.create(SwipeRightApiService::class.java)
    }
}

