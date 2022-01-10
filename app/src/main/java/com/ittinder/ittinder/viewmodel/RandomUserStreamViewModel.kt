package com.ittinder.ittinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.RandomUserApi
import com.ittinder.ittinder.Modules.RandomUserStreamApiService
import com.ittinder.ittinder.data.Chat
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.repository.ChatRepository
import com.ittinder.ittinder.repository.SwipeRepository
import kotlinx.coroutines.launch

private const val TAG = "RandomUserStreamViewModel"

class RandomUserStreamViewModel : ViewModel() {
    private val _RandomUserStreamResponse: MutableLiveData<List<RandomUserStream>> = MutableLiveData()
    val RandomUserStreamResponse: LiveData<List<RandomUserStream>>
        get() = _RandomUserStreamResponse

    fun getUserStream() {
        viewModelScope.launch {
            val RandomUsers: List<RandomUserStream> = SwipeRepository.RandomUser()
            _RandomUserStreamResponse.value = RandomUsers
            Log.i(TAG, RandomUsers.toString())

        }
        init {
            getUserStream()
        }
    }
    private fun init(function: () -> Unit) {
    }
}