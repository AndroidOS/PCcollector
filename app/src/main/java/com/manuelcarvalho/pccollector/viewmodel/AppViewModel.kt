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

    fun storePartsLocally(cartList: List<Part>) {
        launch {
            val dao = PartDatabase(getApplication()).partDao()
            val list = createCartList(cartList)
            dao.deleteAllParts()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            //fetchFromDatabase()
        }
    }

    private fun fetchFromDatabase() {
        //loading.value = true
        launch {
            //val carts = PartDatabase(getApplication()).partDao().deleteAllParts()
            val carts = PartDatabase(getApplication()).partDao().getAllParts()
            Log.d(TAG, "$carts")


        }
    }

    private fun createCartList(partList: List<Part>): List<Part> {
        var list = mutableListOf<Part>()
        for (q in partList) {
            //list.add(Mission1(q.mission_name, q.launch_date_unix.toString()))
        }
        return list
    }


}