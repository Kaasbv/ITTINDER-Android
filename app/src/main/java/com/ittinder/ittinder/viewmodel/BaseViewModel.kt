package com.ittinder.ittinder.viewmodel;

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    companion object {
        var sessionId: String? = null
    }
    fun getSessionId(activity: Activity): String?
    {
        if(sessionId == null){
            val pref = activity.getPreferences(Context.MODE_PRIVATE)
            sessionId = pref.getString("session_id", null).toString()
        }

        return sessionId
    }
}
