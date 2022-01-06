package com.ittinder.ittinder

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.Modules.RandomUserApi
import com.ittinder.ittinder.Modules.RandomUserStreamApiService
import com.ittinder.ittinder.Modules.SwipeLeftApi
import com.ittinder.ittinder.Modules.SwipeRightApi
import kotlinx.coroutines.launch

private const val TAG = "SwipingViewModel"

class SwipingViewModel : ViewModel() {
    private val _swipeLeftResponse: MutableLiveData<String> = MutableLiveData()
    private val _swipeRightResponse: MutableLiveData<String> = MutableLiveData()

    fun postSwipeLeft(swiping: Swiping) {
        viewModelScope.launch {
            SwipeLeftApi.retrofitService.postItem(1, 9)
            _swipeLeftResponse.value = "postSwipeLeft: ${swiping} posted"
        }
    }

    fun postSwipeRight(swiping: Swiping) {
        viewModelScope.launch {
            SwipeRightApi.retrofitService.postItem(1, 9)
            _swipeRightResponse.value = "postSwipeRight: ${swiping} posted"
        }
    }
}