package com.ittinder.ittinder.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ittinder.ittinder.entities.MessageEntity

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("select * from MessageEntity where chatId = :chatId order by createdDate desc limit 1 offset :index")
    suspend fun getMessageByIndex(index: Int, chatId: Long): List<MessageEntity>

    @Query("select count(*) from MessageEntity where chatId = :chatId")
    suspend fun getMessagesCount(chatId: Long): Int
}