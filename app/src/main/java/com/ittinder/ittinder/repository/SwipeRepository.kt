package com.ittinder.ittinder.repository

import com.ittinder.ittinder.Modules.RandomUserApi
import com.ittinder.ittinder.data.RandomUserStream

object SwipeRepository {

    private fun api() = RandomUserApi.retrofitService


    suspend fun RandomUser(): RandomUserStream {
        return api().getUsers("sFg6DFyKnyRuXLFj1UooXlVo5lBBdh1v")
    }
}