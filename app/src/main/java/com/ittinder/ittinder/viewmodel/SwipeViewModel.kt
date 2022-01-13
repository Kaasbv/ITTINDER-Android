package com.ittinder.ittinder.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.SwipeLeftApi
import com.ittinder.ittinder.Modules.SwipeRightApi
import com.ittinder.ittinder.data.Swiping
import kotlinx.coroutines.launch

private const val TAG = "SwipingViewModel"

class SwipingViewModel : ViewModel() {
    private val _swipeLeftResponse: MutableLiveData<String> = MutableLiveData()
    private val _swipeRightResponse: MutableLiveData<String> = MutableLiveData()

    fun postSwipeLeft(user1 : Int , user2 : Int) {
        viewModelScope.launch {
            SwipeLeftApi.retrofitService.postItem(user1, user2)
            _swipeLeftResponse.value = "postSwipeLeft: ${user1} and ${user2} posted"
        }
    }

    fun postSwipeRight(user1 : Int , user2 : Int) {
        viewModelScope.launch {
            SwipeRightApi.retrofitService.postItem(user1, user2)
            _swipeRightResponse.value = "postSwipeRight: ${user1} and ${user2} posted"
        }
    }
}