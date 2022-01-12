package com.ittinder.ittinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String,
    val surname: String,
    val dateOfBirth: String,
    val email: String,
    val gender: String,
    val description: String,
    val interestedInGender: String
) {
}