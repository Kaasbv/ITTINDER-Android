package com.ittinder.ittinder.viewmodel

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
    private val _RandomUserStreamResponse: MutableLiveData<RandomUserStream> = MutableLiveData()
    val RandomUserStreamResponse: LiveData<RandomUserStream>
        get() = _RandomUserStreamResponse

    fun getUserStream() {
        viewModelScope.launch {
            val RandomUsers : RandomUserStream = SwipeRepository.RandomUser()
            _RandomUserStreamResponse.value =  RandomUsers
        }
        init {
            getUserStream()
        }
    }
    private fun init(function: () -> Unit) {
    }
}