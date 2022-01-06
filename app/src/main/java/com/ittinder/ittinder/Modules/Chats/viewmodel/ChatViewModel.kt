package com.ittinder.ittinder.Modules.Chats.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.Chats.domain.Chat
import com.ittinder.ittinder.Modules.Chats.repository.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _result = MutableLiveData<List<Chat>>()
    val result: LiveData<List<Chat>> get() = _result

    fun listChats() {
        viewModelScope.launch {
            val chats: List<Chat> = ChatRepository.listChats()
//            val text = chats.joinToString("\n") { it.toString() }
//            Log.i("aaaaa", "FUCK $text");
            _result.value = chats
        }
    }
}
