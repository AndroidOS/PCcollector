package com.manuelcarvalho.pccollector.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.manuelcarvalho.pccollector.R
import com.manuelcarvalho.pccollector.model.Part
import com.manuelcarvalho.pccollector.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.fragment_list.*

private const val TAG = "ListFragment"

class ListFragment : Fragment(), OnClickListenerInterface {

    private lateinit var viewModel: AppViewModel


    private val myArray = arrayListOf(
        Part(
            "Commodore",
            "VIC-20",
            "Omega Race",
            "Good",
            "1010",
            false,
            "f",
            "V"
        )
    )
    private val listAdapter = ListAdapter(myArray, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[AppViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.carts.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                Log.d(TAG, "list changed")
                listAdapter.updatelist(list)

            }
        })
    }

    override fun onClick() {
        Log.d(TAG, "Onclicked")
    }

}
