package com.ittinder.ittinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.repository.UserRepository
import com.ittinder.ittinder.service.RandomUserApi
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.repository.UserRepository
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

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        getUser()
    }
    init {
        getUserStream()
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                 _user.value = UserRepository.getUser()
                _status.value = "Success: $_user retrieved"
                val userResult = UserRepository.getUser()
                _status.value = userResult
                //_status.value = "Success: ${userResult} retrieved"
            } catch (e: Exception) {
                Log.e(TAG, "Jemoeder")
                //_status.value = "Failure: ${e.message}"
            }
        }
    }

    fun login(email: String, password: String) {
        if (email != user.value?.email && password != user.value?.password) {

        }

    }



    fun getUserStream() {
        viewModelScope.launch {
            val RandomUsers: List<RandomUserStream> = RandomUserApi.retrofitService.getUsers("session_id=emD697PekcqMhwfiUelKdSJcwELcz4Li")
            _RandomUserStreamResponse.value = RandomUsers

        }

    }
}