package com.ittinder.ittinder.data

import java.time.LocalDate

data class User(
    val firstName: String,
    val surname: String,
    val dateOfBirth: LocalDate,
    val email: String,
    val gender: String,
    val description: String,
    val InterestedInGender: String
) {
}