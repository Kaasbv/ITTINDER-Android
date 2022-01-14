package com.ittinder.ittinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val firstName: String,
    val surname: String,
    val dateOfBirth: String,
    val email: String,
    val gender: String?,
    val description: String?,
    val interestedInGender: String?
)
    var id: Int,
    var firstName: String,
    var middleName: String,
    var surname: String,
    var dateOfBirth: String,
    var email: String,
    var gender: String,
    var description: String,
    var interestedInGender: String,
    var password: String
) {
}
