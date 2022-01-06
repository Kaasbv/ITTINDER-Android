package com.ittinder.ittinder.Modules.Chats.repository

import com.ittinder.ittinder.Modules.Chats.domain.Chat
import com.ittinder.ittinder.Modules.Chats.service.ChatApi

object ChatRepository {

    private fun api() = ChatApi.retrofitService
//    private fun dao(context: Context) = TodoistDatabase.getInstance(context).todoItemDao()

    suspend fun listChats(): List<Chat> {
        return api().listChats("session_id=dRNNE2xpfMbh4BxfJm37RhBumEXnfSU4")
    }
}