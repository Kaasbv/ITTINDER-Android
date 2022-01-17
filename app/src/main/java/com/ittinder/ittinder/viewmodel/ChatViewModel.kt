package com.ittinder.ittinder.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.service.ChatApi
import com.ittinder.ittinder.data.Chat
import kotlinx.coroutines.launch

class ChatViewModel : BaseViewModel() {

    private val _result = MutableLiveData<List<Chat>>()
    val result: LiveData<List<Chat>> get() = _result
    private fun api() = ChatApi.retrofitService

    fun listChats(activity: Activity) {
        viewModelScope.launch {
            val chats: List<Chat> = api().listChats("session_id=" + getSessionId(activity))
            _result.value = chats
        }
    }
}
