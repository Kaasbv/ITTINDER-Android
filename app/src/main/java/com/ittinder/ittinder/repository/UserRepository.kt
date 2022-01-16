package com.ittinder.ittinder.repository

import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.service.UserApi
import com.squareup.moshi.Json

object UserRepository {

    private fun api() = UserApi.retrofitService

    suspend fun getUser(): User {
        return api().getUser("session_id=dA373YL49e3AFMfRElzA4A20SfG6DjYe")
    }

    suspend fun updateUser(user: User): String {
        return api().updateUser("session_id=dA373YL49e3AFMfRElzA4A20SfG6DjYe", user)
    }
}