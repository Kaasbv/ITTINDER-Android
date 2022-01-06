package com.ittinder.ittinder.Modules.Chats.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    val id: Int,

    @Json(name = "firstName")
    val firstName: String,

    @Json(name = "image")
    val image: List<String>,
)
