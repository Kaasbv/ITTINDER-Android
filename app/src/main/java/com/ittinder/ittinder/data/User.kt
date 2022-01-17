package com.ittinder.ittinder.data

data class User(
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
    var password: String?,
    var latitude: Double,
    var longitude : Double
)
