package com.manuelcarvalho.pccollector.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.manuelcarvalho.pccollector.R
import com.manuelcarvalho.pccollector.model.Part
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private val myArray = arrayListOf(
        Part(
            "Commodore",
            "VIC-20",
            "Omega Race",
            "Good",
            "1010",
            "8k",
            "f",
            "V"
        )
    )
    private val listAdapter = ListAdapter(myArray)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }


    }
}
