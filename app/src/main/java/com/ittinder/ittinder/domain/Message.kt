package com.ittinder.ittinder.Modules.Chats.domain

import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Message(
    @Json(name = "message")
    val message: String
)
