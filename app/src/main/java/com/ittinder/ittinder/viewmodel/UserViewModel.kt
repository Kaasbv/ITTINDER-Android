package com.ittinder.ittinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.repository.UserRepository
import com.ittinder.ittinder.service.UserApi
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            try {
                 _user.value = UserRepository.getUser()
                _status.value = "Success: $_user retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}