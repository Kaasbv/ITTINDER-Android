package com.ittinder.ittinder.data

data class LoginResponse(
    val sessionId: String,
    val user: User
)