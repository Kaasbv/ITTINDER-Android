package com.ittinder.ittinder.repository

import com.ittinder.ittinder.Modules.Chats.service.ChatApi
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.Image
import com.ittinder.ittinder.service.ImageApi
import retrofit2.http.Multipart

class ImageRepository {

    object ImageRepository {

        private fun api() = ImageApi.retrofitService
//    private fun dao(context: Context) = TodoistDatabase.getInstance(context).todoItemDao()

        suspend fun uploadImage(): Image {
            return api().uploadImage("session_id=u3wySCEUzP1LaEym8cMZWhc6hh4FAWvz", Multipart())
        }
    }
}