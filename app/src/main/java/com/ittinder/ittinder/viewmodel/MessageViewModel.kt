package com.ittinder.ittinder.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.service.ChatApi
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
        val pref = activity.getPreferences(Context.MODE_PRIVATE)
        val lastSyncIso = pref.getString("last_sync_iso", "").toString()

        Log.i("Fuck", lastSyncIso)

        viewModelScope.launch {
            val response = api().listMessagesLastSyncBased("session_id=" + getSessionId(activity), lastSyncIso)

            with(pref.edit()){
                this.putString("last_sync_iso", response.currentIso)
                apply()
            }

            if(response.messages.isNotEmpty()){
                MessageRepository.insertMessages(context, response.messages)
                count = MessageRepository.getMessagesCount(context, chatId)
                _lastChanged.value = response.currentIso
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
