package com.ittinder.ittinder.service
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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


interface SwipingApiService {
    @POST("/SwipeLeft")
    suspend fun swipeLeft(@Query("idUser1") idUser1: Long, @Query("idUser2") idUser2: Long)

    @POST ("/SwipeRight")
    suspend fun swipeRight(@Query("idUser1") idUser1: Long, @Query("idUser2") idUser2: Long)
}
object SwipingApi {
    val retrofitService: SwipingApiService by lazy {
        retrofit.create(SwipingApiService::class.java)
    }
}

