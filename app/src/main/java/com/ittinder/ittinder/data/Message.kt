package com.ittinder.ittinder.data

import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Message(
    val id: Long,
    val createdDate: String,
    val message: String,
    val user: User
)
