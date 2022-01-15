package com.ittinder.ittinder.repository

import com.ittinder.ittinder.Modules.Chats.service.ChatApi
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.service.UserApi
import com.squareup.moshi.Json

object UserRepository {

    private fun api() = UserApi.retrofitService

    suspend fun getUser(): User {
        return api().getUser("session_id=LMX9a5XDZz8ZPt8nvCJe1nyqZDPgDSFl")
    }

    suspend fun updateUser(user: User): String {
        return api().updateUser("session_id=LMX9a5XDZz8ZPt8nvCJe1nyqZDPgDSFl", user)
    }
}