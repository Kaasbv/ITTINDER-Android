package com.ittinder.ittinder.data

data class Message(
    val id: Long,
    val createdDate: String,
    val message: String,
    val user: User?,
    val chat: Chat?
)
