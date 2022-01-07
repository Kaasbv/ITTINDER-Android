package com.ittinder.ittinder.data

import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Message(
    val message: String
)
