package com.ittinder.ittinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var firstName: String,
    var middleName: String?,
    var surname: String,
    var dateOfBirth: String,
    var email: String,
    val image: String?,
    var gender: String?,
    var description: String?,
    var interestedInGender: String?,
    var password: String?
) {
}
