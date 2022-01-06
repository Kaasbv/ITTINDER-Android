package com.ittinder.ittinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.RandomUserApi
import com.ittinder.ittinder.Modules.RandomUserStreamApiService
import kotlinx.coroutines.launch

private const val TAG = "RandomUserStreamViewModel"

class RandomUserStreamViewModel : ViewModel() {
    private val _RandomUserStreamResponse: MutableLiveData<String> = MutableLiveData()
    val RandomUserStreamResponse: LiveData<String>
        get() = _RandomUserStreamResponse

    fun getUserStream() {
        viewModelScope.launch {
            _RandomUserStreamResponse.value =  RandomUserApi.retrofitService.getUsers()
        }
        init {
            getUserStream()
        }
    }
    private fun init(function: () -> Unit) {
    }
}