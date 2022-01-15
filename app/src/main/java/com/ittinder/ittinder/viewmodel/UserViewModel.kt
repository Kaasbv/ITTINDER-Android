package com.ittinder.ittinder.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.data.LoginObject
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.repository.UserRepository
import com.ittinder.ittinder.service.RandomUserApi
import com.ittinder.ittinder.service.UserApi

import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "UserViewModel"

class UserViewModel : ViewModel() {
    private val _status = MutableLiveData<User>()
    val status: LiveData<User> get () = _status

    private val _RandomUserStreamResponse: MutableLiveData<List<RandomUserStream>> = MutableLiveData()
    val RandomUserStreamResponse: LiveData<List<RandomUserStream>>
        get() = _RandomUserStreamResponse

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
//        getUser()
//        getUserStream()
    }


    fun getUser() {
        viewModelScope.launch {
            try {
                 _user.value = UserRepository.getUser()
//                _status.value = "Success: $_user retrieved"
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

    fun login(email: String, password: String, activity: Activity): MutableLiveData<Boolean> {
        val response = MutableLiveData<Boolean>()

        val pref = activity?.getPreferences(Context.MODE_PRIVATE)

        viewModelScope.launch {
            try{
                val apiResponse = UserApi.retrofitService.loginUser(LoginObject(email, password))
                with (pref.edit()) {
                    putString("session_id", apiResponse.sessionId)
                    putString("user_id", apiResponse.user.id.toString())
                    apply()
                }

                response.value = true
            }catch(e: Exception){
                response.value = false
            }
        }

        return response
    }

    fun getUserStream() {
        viewModelScope.launch {
            try {
                val RandomUsers: List<RandomUserStream> = RandomUserApi.retrofitService.getUsers("session_id=LMX9a5XDZz8ZPt8nvCJe1nyqZDPgDSFl")
                _RandomUserStreamResponse.value = RandomUsers
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve users from getUserStream")
            }

        }

    }
}