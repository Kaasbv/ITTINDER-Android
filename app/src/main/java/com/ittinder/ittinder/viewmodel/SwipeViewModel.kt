package com.ittinder.ittinder.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.SwipingApi
import com.ittinder.ittinder.data.Swiping
import kotlinx.coroutines.launch

private const val TAG = "SwipingViewModel"

class SwipingViewModel : ViewModel() {
    private val _swipeLeftResponse: MutableLiveData<String> = MutableLiveData()
    private val _swipeRightResponse: MutableLiveData<String> = MutableLiveData()

    fun postSwipeLeft(user1 : Int , user2 : Int) {
        viewModelScope.launch {
            SwipingApi.retrofitService.swipeLeft(user1, user2)
            _swipeLeftResponse.value = "postSwipeLeft: ${user1} and ${user2} posted"
        }
    }

    fun postSwipeRight(user1 : Int , user2 : Int) {
        viewModelScope.launch {
            SwipingApi.retrofitService.swipeRight(user1, user2)
            _swipeRightResponse.value = "postSwipeRight: ${user1} and ${user2} posted"
        }
    }
}