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

    private fun api() = RandomUserApi.retrofitService

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

    fun getUserStream() {
        viewModelScope.launch {
            val RandomUsers: List<RandomUserStream> = api().getUsers("session_id=emD697PekcqMhwfiUelKdSJcwELcz4Li")
            _RandomUserStreamResponse.value = RandomUsers
            Log.e(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
            Log.e(TAG, RandomUsers.toString())

        }

    }
}