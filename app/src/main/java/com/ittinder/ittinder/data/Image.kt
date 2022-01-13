package com.ittinder.ittinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val user: User,
    val sortNr: Int,
    val profileImagePath: String
)