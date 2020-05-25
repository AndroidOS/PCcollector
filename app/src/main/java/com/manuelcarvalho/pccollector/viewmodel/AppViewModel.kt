package com.manuelcarvalho.pccollector.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manuelcarvalho.pccollector.model.Part
import com.manuelcarvalho.pccollector.model.PartDatabase
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {

    val carts by lazy { MutableLiveData<List<Part>>() }
    val fabDisplay = MutableLiveData<Boolean>()

    fun refresh() {
        Log.w(TAG, "")
        launch {
            fetchFromDatabase()
        }
    }

    fun changeOwn(num: Int) {
        Log.d(TAG, "changeOwn $num")
    }

    fun storePartsLocally(cartList: List<Part>) {
        launch {
            //Log.w(TAG, "list $cartList")
            val dao = PartDatabase(getApplication()).partDao()
            dao.deleteAllParts()
            val result = dao.insertAll(*cartList.toTypedArray())
            //Log.w(TAG, "store result $result")
            var i = 0
            while (i < cartList.size) {
                cartList[i].uuid = result[i].toInt()
                ++i
            }
            fetchFromDatabase()
        }
    }

    private fun fetchFromDatabase() {
        //loading.value = true
        launch {
            val cartList = PartDatabase(getApplication()).partDao().getAllParts()
            carts.value = cartList
        }
    }


}