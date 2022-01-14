package com.ittinder.ittinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val initiatedUser: User,
    val affectedUser: User,
    val lastMessage: Message?
)
