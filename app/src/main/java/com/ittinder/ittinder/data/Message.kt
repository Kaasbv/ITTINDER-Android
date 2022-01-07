package com.ittinder.ittinder.domain

import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Message(
    val message: String
)
