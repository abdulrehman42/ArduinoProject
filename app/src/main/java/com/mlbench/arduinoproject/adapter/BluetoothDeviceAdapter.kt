package com.mlbench.arduinoproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mlbench.arduinoproject.R
import com.mlbench.arduinoproject.model.BluetoothData

class BluetoothDeviceAdapter(
    val context:Context,
    private var mList: List<BluetoothData>,
    val listner:ItemInterface
) :

    RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout
                    .custom_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.item.text = itemsViewModel.name
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"Please Wait...",Toast.LENGTH_SHORT).show()
            listner.selectItem(itemsViewModel.name.toString(),itemsViewModel.address.toString())

        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var item: TextView = itemView.findViewById(R.id.device_item)
    }

    interface ItemInterface
    {
        fun selectItem(name:String,address:String)
    }
}