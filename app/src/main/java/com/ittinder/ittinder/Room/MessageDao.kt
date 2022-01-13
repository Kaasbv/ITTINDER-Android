package com.ittinder.ittinder.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ittinder.ittinder.entities.MessageEntity

@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("select * from MessageEntity where userId = :userId order by createdDate desc limit 1 offset :index")
    suspend fun getMessageByIndex(index: Int, userId: Int): List<MessageEntity>

    @Query("select count(*) from MessageEntity where userId = :userId")
    suspend fun getMessagesCount(userId: Int): Int
}