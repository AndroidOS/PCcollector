package com.manuelcarvalho.pccollector.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manuelcarvalho.pccollector.R
import com.manuelcarvalho.pccollector.model.Part
import com.manuelcarvalho.pccollector.utils.sendEmail
import com.manuelcarvalho.pccollector.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppViewModel
    var manu1 = arrayOf<CharSequence>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        viewModel = ViewModelProviders.of(this)[AppViewModel::class.java]
        viewModel.refresh()



        //val exampleItems: Array<CharSequence?>? = exampleList?.size?.let { it1 -> Array(it1,{ i -> exampleArray?.get(i) }) }

//        val charSequenceItems: Array<CharSequence> =
//            manu.toArray(arrayOfNulls<CharSequence>(manu.size()))
        // Log.d(TAG, "$dialogueItems")

        //manu1 = dialogueItems

        observeViewModel()
        viewModel.fabDisplay.value = false
        //viewModel.getManufacturers()
        fab.setOnClickListener { view ->
            //bulkData()
            //viewModel.refresh()


            sendEmail(this, makeList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            R.id.action_query -> {
                val a = dialogueQuery(manu1)
                Log.d(TAG, "Options $a")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    private fun observeViewModel() {

        viewModel.fabDisplay.observe(this, Observer { fabBut ->
            fabBut?.let {
                if (fabBut == false) {
                    fab.hide()
                } else {
                    fab.show()
                }
            }
        })

    }

    private fun bulkData() {
        var cartList = mutableListOf<Part>()
        var manufacturer = ""
        var cartName = ""
        var yearMan = ""
        try {
            val inputStream: InputStream = assets.open("c64cartList.txt")
            val text = inputStream.bufferedReader().use { it.readText() }

            val lines = text.split("\n")

            for (line in lines) {

                if (line[0] == '*') {

                    manufacturer = line.substringAfter('*')
                    //Log.d(TAG,"$manufacturer")
                } else {
                    cartName = line.substringBefore(',')
                    if (line.contains(',')) {
                        yearMan = line.substringAfter(',')
                    } else {
                        yearMan = "???"
                    }

                    //Log.d(TAG, "$yearMan")
                }
                val part = Part(
                    manufacturer,
                    "Commodore 64",
                    cartName,
                    "good",
                    "123456",
                    true,
                    "very",
                    yearMan
                )
                //Log.d(TAG, "$part")
                cartList.add(part)
            }


            viewModel.storePartsLocally(cartList)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    fun dialogueQuery(items: Array<CharSequence>): String {

        val manu: List<String> = viewModel.manufacturers.value!!
        val dialogueItems: Array<CharSequence?>? =
            manu.size.let { it1 -> Array(it1, { i -> manu.get(i) }) }


        var manu2 = ""
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)

            builder.apply {
                setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })

                setTitle("Choose Cartridge")

                
                builder.setItems(
                    dialogueItems,

                    DialogInterface.OnClickListener { dialog, which ->
                        manu2 = dialogueItems?.get(which) as String
                        Log.d(TAG, " onClick $manu2   $which")
                        viewModel.queryManufacturer(manu2)

                    })

            }

            builder.create()
        }

        alertDialog?.show()

        return manu2

    }

    private fun makeList(): List<String> {
        var carts = mutableListOf<String>()
        val cartList = viewModel.carts.value
        if (cartList != null) {
            for (n in cartList) {
                carts.add(n.catridge)
            }
        }

        return carts
    }
}
