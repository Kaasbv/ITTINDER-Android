package com.ittinder.ittinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RandomUserStream(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String,
    val surname: String,
    val dateOfBirth: String,
    val email: String,
    val password: String,
    val gender: String,
    val interestedInGender: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val image: List<Image>
)