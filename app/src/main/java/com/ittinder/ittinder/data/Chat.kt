package com.ittinder.ittinder.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val initiatedUser: User,
    val affectedUser: User,
    val lastMessage: Message
)
