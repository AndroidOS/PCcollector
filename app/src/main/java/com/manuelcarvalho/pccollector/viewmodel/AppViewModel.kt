package com.manuelcarvalho.pccollector.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manuelcarvalho.pccollector.model.Part
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    val carts by lazy { MutableLiveData<List<Part>>() }
    val fabDisplay = MutableLiveData<Boolean>()

    fun refresh() {
        Log.w(TAG, "")
        launch {

        }
    }


}