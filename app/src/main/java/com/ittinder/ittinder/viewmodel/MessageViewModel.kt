package com.ittinder.ittinder.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.Chats.service.ChatApi
import com.ittinder.ittinder.data.Message
import com.ittinder.ittinder.entities.MessageEntity
import com.ittinder.ittinder.repository.MessageRepository
import kotlinx.coroutines.launch
import java.time.Instant

class MessageViewModel : BaseViewModel() {

    private val _lastChanged = MutableLiveData<String>()
    val lastChanged: LiveData<String> get() = _lastChanged
    var count: Int = 0

    private fun api() = ChatApi.retrofitService

    fun sync(context: Context, activity: Activity, chatId: Long) {
        //First get last sync date
        val currentIso: String = Instant.now().toString()

        viewModelScope.launch {
            val messages: List<Message> = api().listMessagesPolling("session_id=" + getSessionId(activity), 1)

            if(messages.isNotEmpty()){
                MessageRepository.insertMessages(context, messages)
                count = MessageRepository.getMessagesCount(context, chatId)
                _lastChanged.value = currentIso
            }
        }
    }

    fun getMessageByPositionAndUserId(context: Context, position: Int, chatId: Long): MutableLiveData<MessageEntity>
    {
        val responseLiveData = MutableLiveData<MessageEntity>()
        viewModelScope.launch {
            responseLiveData.value = MessageRepository.getMessageEntityByIndex(context, position, chatId)
        }
        return responseLiveData
    }

    fun getMessagesCountByUserId(context: Context, chatId: Long): MutableLiveData<Int>
    {
        val responseLiveData = MutableLiveData<Int>()
        viewModelScope.launch {
            responseLiveData.value = MessageRepository.getMessagesCount(context, chatId)
        }
        return responseLiveData
    }

    fun postMessage(chatId: Long, message: String, activity: Activity)
    {
        viewModelScope.launch {
            api().postMessage("session_id=" + getSessionId(activity), chatId, message)
        }
    }
}
