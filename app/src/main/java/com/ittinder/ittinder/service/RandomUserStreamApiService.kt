package com.ittinder.ittinder.Modules
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "http://10.0.2.2:8080/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface RandomUserStreamApiService {
    @GET("user/stream")
    suspend fun getUsers(): String
}

object RandomUserApi {
    val retrofitService: RandomUserStreamApiService by lazy {
        retrofit.create(RandomUserStreamApiService::class.java)
    }
}

interface SwipeLeftApiService {
    @POST("SwipeLeft")
    suspend fun postItem(@Query("idUser1") idUser1: Int, @Query("idUser2") idUser2: Int)
}
object SwipeLeftApi {
    val retrofitService: SwipeLeftApiService by lazy {
        retrofit.create(SwipeLeftApiService::class.java)
    }
}

interface  SwipeRightApiService {
    @POST ("SwipeRight")
    suspend fun postItem(@Query("idUser1") idUser1: Int, @Query("idUser2") idUser2: Int)
}

object SwipeRightApi {
    val retrofitService: SwipeRightApiService by lazy {
        retrofit.create(SwipeRightApiService::class.java)
    }
}