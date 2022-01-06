package com.ittinder.ittinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.service.UserApi
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                val userResult = UserApi.retrofitService.getUser()
                _status.value = "Success: $userResult retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}