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
    val dateOfBirth: LocalDate,
    val email: String,
    val password: String,
    val gender: String,
    val description: String,
    val InterestedInGender: String
) {
}