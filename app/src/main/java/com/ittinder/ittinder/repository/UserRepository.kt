package com.ittinder.ittinder.repository

import com.ittinder.ittinder.Modules.Chats.service.ChatApi
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.service.UserApi

object UserRepository {

    private fun api() = UserApi.retrofitService

    suspend fun getUser(): User {
        return api().getUser("session_id=UyPzVnyD6k5cCmjVZndXghPc6rGE5t9K")
    }
}