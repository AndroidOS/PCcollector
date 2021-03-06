package com.manuelcarvalho.pccollector.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.manuelcarvalho.pccollector.R
import com.manuelcarvalho.pccollector.model.Part
import kotlinx.android.synthetic.main.list_view.view.*


private const val TAG = "ListAdapter"

class ListAdapter(
    val cartList: ArrayList<Part>,
    private val callback: OnClickListenerInterface

) :
    RecyclerView.Adapter<ListAdapter.CartViewHolder>() {

    fun updatelist(cartList1: List<Part>) {
        cartList.clear()
        cartList.addAll(cartList1)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(
            R.layout.list_view
            , parent, false
        )
        return CartViewHolder(view)
    }

    override fun getItemCount() = cartList.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.view.tv_cart.text = cartList[position].catridge
        holder.view.tv_manufacturer.text = cartList[position].manufacturer
        holder.view.checkBox.isChecked = cartList[position].ownIt

        holder.view.checkBox.setOnClickListener {
            val num = cartList[position].uuid
            Log.d(TAG, "${cartList[position].uuid} clicked")
            callback.onClick(num)
        }


        holder.view.setOnClickListener {
            val cart = cartList[position].catridge
            Navigation.findNavController(it)
                .navigate(ListFragmentDirections.actionFirstFragmentToDetailFragment(cart))


        }


    }

    //.navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
    class CartViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}

interface OnClickListenerInterface {
    fun onClick(num: Int)
    fun onChoice(string: String)
}