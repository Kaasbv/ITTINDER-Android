package com.ittinder.ittinder.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.repository.UserRepository
import com.ittinder.ittinder.service.RandomUserApi
import com.ittinder.ittinder.service.UserApi
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UserStreamModel"

class UserViewModel : ViewModel() {
    private val _status = MutableLiveData<User>()
    val status: LiveData<User> get () = _status

    private val _RandomUserStreamResponse: MutableLiveData<List<RandomUserStream>> = MutableLiveData()
    val RandomUserStreamResponse: LiveData<List<RandomUserStream>>
        get() = _RandomUserStreamResponse

    init {
        getUser()
    }
    init {
        getUserStream()
    }


    fun getUser() {
        viewModelScope.launch {
            try {
                val userResult = UserRepository.getUser()
                _status.value = userResult
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve user from getUser")
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                UserRepository.updateUser(user)
            }
            catch (e: Exception){
                Log.e(TAG, "Can't update user from updateUser")
            }
        }
    }

    fun getUserStream() {
        viewModelScope.launch {
            try {
                val RandomUsers: List<RandomUserStream> = RandomUserApi.retrofitService.getUsers("session_id=dA373YL49e3AFMfRElzA4A20SfG6DjYe")
                _RandomUserStreamResponse.value = RandomUsers
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve users from getUserStream")
            }

        }

    }
}