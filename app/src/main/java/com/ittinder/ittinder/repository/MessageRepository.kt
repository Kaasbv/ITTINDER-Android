package com.ittinder.ittinder.repository

import android.content.Context
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.Modules.Chats.service.ChatApi
import com.ittinder.ittinder.Room.MainDatabase
import com.ittinder.ittinder.data.Message
import com.ittinder.ittinder.entities.MessageEntity
import androidx.lifecycle.viewModelScope

object MessageRepository {
    private fun dao(context: Context) = MainDatabase.getInstance(context).messageDao()

    suspend fun insertMessages(context: Context, messages: List<Message>) {
        //Remap messages
        var messageEntities: MutableList<MessageEntity> = mutableListOf()

        messages.forEach {
            messageEntities.add(MessageEntity(
                it.id,
                it.message,
                it.user.firstName,
                it.createdDate,
                it.user.id
            ))
        }

        dao(context).insertMessages(messageEntities.toList())
    }

    suspend fun getMessageEntityByIndex(context: Context, index: Int, userId: Int): MessageEntity
    {
        val messages: List<MessageEntity> = dao(context).getMessageByIndex(index, userId)
        return messages[0]
    }

    suspend fun getMessagesCount(context: Context, userId: Int): Int
    {
        return dao(context).getMessagesCount(userId)
    }
}