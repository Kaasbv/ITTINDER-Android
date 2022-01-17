package com.ittinder.ittinder.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.data.LoginObject
import com.ittinder.ittinder.data.RandomUserStream
import com.ittinder.ittinder.data.RegisterObject
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.service.RandomUserApi
import com.ittinder.ittinder.service.UserApi

import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream
import java.lang.Exception

private const val TAG = "UserViewModel"

class UserViewModel : BaseViewModel() {
    private val _RandomUserStreamResponse: MutableLiveData<List<RandomUserStream>> = MutableLiveData()
    val RandomUserStreamResponse: LiveData<List<RandomUserStream>>
        get() = _RandomUserStreamResponse

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private fun api() = UserApi.retrofitService

    fun getUser(activity: Activity) {
        viewModelScope.launch {
            try {
                val userResult = api().getUser("session_id=" + getSessionId(activity))
                 _user.value = userResult
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve user from getUser")
            }
        }
    }

    fun updateUser(user: User, activity: Activity) {
        viewModelScope.launch {
            try {
                api().updateUser("session_id=" + getSessionId(activity), user)
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
                    putLong("user_id", apiResponse.user.id)
                    apply()
                }

                response.value = true
            }catch(e: Exception){
                response.value = false
            }
        }

        return response
    }

    fun getUserStream(activity: Activity) {
        viewModelScope.launch {
            try {
                val RandomUsers: List<RandomUserStream> = RandomUserApi.retrofitService.getUsers("session_id=" + getSessionId(activity))
                _RandomUserStreamResponse.value = RandomUsers
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve users from getUserStream")
                throw e
            }

        }
    }

    fun register(user: RegisterObject): MutableLiveData<Boolean>
    {
        val response = MutableLiveData<Boolean>()
        Log.i("Ding", "Is send broski")
        viewModelScope.launch {
            try {
                UserApi.retrofitService.register(user)
                response.value = true
            }catch(e: Exception){
                response.value = false
            }
        }

        return response
    }

    fun uploadImage(activity: Activity, inputStream: InputStream) {
        viewModelScope.launch {
            val part = MultipartBody.Part.createFormData(
                "multipartFile", "image.jpg", RequestBody.create(
                    MediaType.parse("image/*"),
                    inputStream.readBytes()
                )
            )
            val addImage = api().uploadImage("session_id=" + getSessionId(activity), part)
        }
    }
}