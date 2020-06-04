package com.manuelcarvalho.pccollector.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.manuelcarvalho.pccollector.model.Part
import com.manuelcarvalho.pccollector.model.PartDatabase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch


private const val TAG = "AppViewModel"

class AppViewModel(application: Application) : BaseViewModel(application) {


    val carts by lazy { MutableLiveData<List<Part>>() }
    val fabDisplay = MutableLiveData<Boolean>()

    val manufacturers by lazy { MutableLiveData<List<String>>() }

    protected val disposable = CompositeDisposable()

    private var dataBaseInstance = PartDatabase.invoke(application)

    fun refresh() {
        Log.w(TAG, "")
        launch {
            fetchFromDatabase()

        }
    }

    fun changeOwn(num: Int) {
        //Log.d(TAG, "changeOwn $num")
        fetchPartDatabase(num)
    }

    fun queryManufacturer(str: String) {
        launch {
            val dao = PartDatabase(getApplication()).partDao()

            val result = dao.getManuParts(str)

            carts.value = result
            Log.d(TAG, "viewmodel $result")

        }

    }

    fun getManufacturers() {
        var manufacturers1 = mutableListOf<String>()
        for (n in carts.value!!) {
            manufacturers1.add(n.manufacturer)
        }
        manufacturers.value = manufacturers1
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
            var manufacturers1 = mutableListOf<String>()
            carts.value = cartList
            for (n in carts.value!!) {
                if (!manufacturers1.contains(n.manufacturer)) {
                    manufacturers1.add(n.manufacturer)
                }
            }
            manufacturers.value = manufacturers1
        }
    }

    private fun fetchPartDatabase(uuid: Int) {
        //loading.value = true
        launch {
            val cart = PartDatabase(getApplication()).partDao().getPart(uuid)
            if (cart.ownIt == true) {
                    changeSinglePart(uuid, false)
            } else {
                changeSinglePart(uuid, true)
            }
        }
    }

    private fun changeSinglePart(uuid: Int, boolean: Boolean) {
        launch {
            dataBaseInstance.partDao().updateOwnIt(uuid, boolean)
        }
    }









}