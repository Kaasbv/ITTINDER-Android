package com.ittinder.ittinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.repository.UserRepository
import com.ittinder.ittinder.service.UserApi
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UserStreamModel"

class UserViewModel : ViewModel() {

    private val _status = MutableLiveData<User>()
    val status: LiveData<User> get () = _status

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                val userResult = UserRepository.getUser()
                _status.value = userResult
                //_status.value = "Success: ${userResult} retrieved"
            } catch (e: Exception) {
                Log.e(TAG, "Jemoeder")
                //_status.value = "Failure: ${e.message}"
            }
        }
    }
}