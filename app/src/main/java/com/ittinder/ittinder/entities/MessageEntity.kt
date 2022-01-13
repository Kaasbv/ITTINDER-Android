package com.ittinder.ittinder.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val message: String,
    val name: String,
    val createdDate: String,
    val userId: Long
)