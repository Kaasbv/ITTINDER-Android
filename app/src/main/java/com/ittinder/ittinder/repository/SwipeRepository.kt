package com.ittinder.ittinder.repository

import com.ittinder.ittinder.Modules.RandomUserApi
import com.ittinder.ittinder.data.RandomUserStream

object SwipeRepository {

    private fun api() = RandomUserApi.retrofitService


    suspend fun RandomUser(): List<RandomUserStream> {
        return api().getUsers("session_id=emD697PekcqMhwfiUelKdSJcwELcz4Li")
    }
}