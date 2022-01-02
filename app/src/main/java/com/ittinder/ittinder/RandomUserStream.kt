package com.ittinder.ittinder

import android.media.Image


data class RandomUserStream(
    val id: Int,
    val firstName: String,
    val surname: String,
    val dateOfBirth: Int,
    val email: String,
    val password: String,
    val gender: String,
    val interestedInGender: String,
    val description: String,
    val image: Image,
    val latitude: Double,
    val longitude: Double,
)