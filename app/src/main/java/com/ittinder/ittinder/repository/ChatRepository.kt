package com.ittinder.ittinder.repository

import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.Modules.Chats.service.ChatApi

object ChatRepository {

    private fun api() = ChatApi.retrofitService
//    private fun dao(context: Context) = TodoistDatabase.getInstance(context).todoItemDao()

    suspend fun listChats(): List<Chat> {
        return api().listChats("session_id=VFkT4CRrYVcLvfloNlYFvB8M9tTclhxw")
    }
}