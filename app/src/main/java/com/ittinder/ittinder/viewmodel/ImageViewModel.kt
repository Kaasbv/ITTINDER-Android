package com.ittinder.ittinder.viewmodel

import android.se.omapi.Session
import com.ittinder.ittinder.data.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ittinder.ittinder.service.ImageApi
import com.ittinder.ittinder.service.ImageApiService
import kotlinx.coroutines.launch
import retrofit2.http.Multipart
import retrofit2.http.Part
import java.lang.Exception

class ImageViewModel : ViewModel() {


    private val _status = MutableLiveData<String>()

    val status: LiveData<String> = _status

    private val _photo = MutableLiveData<Image>()
    val photo: LiveData<Image> = _photo

    init {
        postPhoto()
    }

    private fun postPhoto() {

        viewModelScope.launch {
            try {
                //TODO Dno of dit zo werkt...
                _photo.value = ImageApi.retrofitService.uploadImage(session_id = "u3wySCEUzP1LaEym8cMZWhc6hh4FAWvz", multipart = Multipart())
                _status.value = "Success: Photo uploaded"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
