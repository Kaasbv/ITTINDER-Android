package com.ittinder.ittinder.Modules.Chats.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    val id: Int
)
