package com.ittinder.ittinder.data

data class Chat(
    val id: Long,
    val initiatedUser: User,
    val affectedUser: User,
    val lastMessage: Message?
)
