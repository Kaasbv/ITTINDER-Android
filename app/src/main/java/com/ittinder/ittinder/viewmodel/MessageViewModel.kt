package com.ittinder.ittinder.viewmodel

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.Chats.service.ChatApi
import com.ittinder.ittinder.Modules.Chats.service.ChatApiService
import com.ittinder.ittinder.R
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.Message
import com.ittinder.ittinder.entities.MessageEntity
import com.ittinder.ittinder.repository.ChatRepository
import com.ittinder.ittinder.repository.MessageRepository
import kotlinx.coroutines.launch
import java.time.Instant

class MessageViewModel : ViewModel() {

    private val _lastChanged = MutableLiveData<String>()
    val lastChanged: LiveData<String> get() = _lastChanged
    var count: Int = 0

    private fun api() = ChatApi.retrofitService

    fun sync(context: Context, activity: Activity) {
        //First get last sync date
        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        var lastSyncIso = pref.getString("messages_last_sync_iso", null)

        if(lastSyncIso == null) lastSyncIso = "1900-01-01T00:00:00"
        val currentIso: String = Instant.now().toString()

        Log.i("FUCK", currentIso)
        Log.i("FUCK", lastSyncIso)

        viewModelScope.launch {
            val messages: List<Message> = api().listMessagesPolling("session_id=pY586JSDnWfqZtwdAEmPlr1MAv6JLwhn", 1)
            Log.i("MESSAGES", messages.toString())

            if(messages.isNotEmpty()){
                MessageRepository.insertMessages(context, messages)
                count = MessageRepository.getMessagesCount(context, 18)
                _lastChanged.value = currentIso
            }

            with (pref.edit()) {
                putString("messages_last_sync_iso", currentIso)
                apply()
            }
        }
    }

    fun getMessageByPositionAndUserId(context: Context, position: Int, userId: Int): MutableLiveData<MessageEntity>
    {
        val responseLiveData = MutableLiveData<MessageEntity>()
        viewModelScope.launch {
            responseLiveData.value = MessageRepository.getMessageEntityByIndex(context, position, userId)
        }
        return responseLiveData
    }

    fun getMessagesCountByUserId(context: Context, userId: Int): MutableLiveData<Int>
    {
        val responseLiveData = MutableLiveData<Int>()
        viewModelScope.launch {
            responseLiveData.value = MessageRepository.getMessagesCount(context, userId)
        }
        return responseLiveData
    }
}
