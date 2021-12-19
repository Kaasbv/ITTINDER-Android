package com.ittinder.ittinder.Modules.Chats.repository

import android.content.Context
import com.ittinder.ittinder.Modules.Chats.domain.Chat
//import android.widget.Toast
import com.ittinder.ittinder.Modules.Chats.service.ChatApi
//import nl.spijkerman.ivo.todoist.room.TodoistDatabase


object ChatRepository {

    private fun api() = ChatApi.retrofitService
//    private fun dao(context: Context) = TodoistDatabase.getInstance(context).todoItemDao()

    suspend fun listChats(): List<Chat> {
        return api().listChats()
    }
}