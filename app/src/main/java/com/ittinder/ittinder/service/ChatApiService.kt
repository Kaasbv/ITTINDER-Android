package com.ittinder.ittinder.service

import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.Message
import com.ittinder.ittinder.data.MessagesWithIso
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import retrofit2.http.Header


private const val BASE_URL = "http://51.158.171.172:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//var dateAdapter: JsonAdapter<Date> = moshi.adapter<Date>(Date::class.java)

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ChatApiService {
    @GET("/user/chats")
    suspend fun listChats(@Header("Cookie") session_id: String): List<Chat>

    @GET("/user/messages/polling")
    suspend fun listMessagesPolling(@Header("Cookie") session_id: String, @Query("secondsRange") secondsRange: Int): List<Message>

    @GET("/user/messages/lastSyncBased")
    suspend fun listMessagesLastSyncBased(@Header("Cookie") session_id: String, @Query("lastSyncIso") lastSyncIso: String): MessagesWithIso

    @POST("/chat/{chatId}/messages")
    suspend fun postMessage(@Header("Cookie") session_id: String, @Path("chatId") chatId: Long, @Body message: String)
}

object ChatApi {
    val retrofitService: ChatApiService by lazy {
        retrofit.create(ChatApiService::class.java)
    }
}