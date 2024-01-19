package com.mlbench.arduinoproject

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.mlbench.arduinoproject.adapter.BluetoothDeviceAdapter
import com.mlbench.arduinoproject.chat.ChatActivity
import com.mlbench.arduinoproject.databinding.ActivityMainBinding
import com.mlbench.arduinoproject.model.BluetoothData
import java.io.IOException
import java.util.UUID

class MainActivity : AppCompatActivity(), BluetoothHelperListener,BluetoothDeviceAdapter.ItemInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var bluetoothAdapter1: BluetoothAdapter
    lateinit var bluetoothHelper:BluetoothHelper
    private lateinit var bluetoothAdapter: BluetoothDeviceAdapter
    lateinit var sharePref: SharePref
    val list= arrayListOf<BluetoothData>()
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharePref= SharePref(this)
        bluetoothAdapter1 = BluetoothAdapter.getDefaultAdapter()

        bluetoothHelper = BluetoothHelper(this, this)
            .setPermissionRequired(true)
            .create()
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter1.bondedDevices
        if (pairedDevices!!.isNotEmpty())
        {
            startActivity(Intent(this,ChatActivity::class.java))
        }else{
            initView()
        }
    }

    private fun initView() {
        bluetoothAdapter=BluetoothDeviceAdapter(this,list,this)
        binding.listItem.adapter = bluetoothAdapter
        bluetoothHelper.startDiscovery()
    /*        if (bluetoothHelper.isBluetoothEnabled()) bluetoothHelper.disableBluetooth()
        else bluetoothHelper.enableBluetooth()*/
    }


    override fun onResume() {
        super.onResume()
        bluetoothHelper.registerBluetoothStateChanged()
    }

    override fun onPause() {
        super.onPause()
        bluetoothHelper.unregisterBluetoothStateChanged()
    }

    @SuppressLint("MissingPermission")
    override fun getBluetoothDeviceList(device: BluetoothDevice?) {
        if (!device?.name.isNullOrEmpty())
        {
            list.add(BluetoothData(device?.name,device?.address))
        }
   //     bluetoothAdapter.updateData(list)
        bluetoothAdapter.notifyDataSetChanged()
    }

    override fun onDisabledBluetooh() {
        Toast.makeText(this,"BLuetooth Off",Toast.LENGTH_SHORT).show()
    }

    override fun onEnabledBluetooth() {
        Toast.makeText(this,"BLuetooth On",Toast.LENGTH_SHORT).show()
    }

    override fun onFinishDiscovery() {

    }

    override fun onStartDiscovery() {
        Toast.makeText(this,"Please Wait",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    override fun selectItem(name:String,address:String) {

        Thread {
            try {
                val device = bluetoothAdapter1.getRemoteDevice(address)
                val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                val socket = device.createRfcommSocketToServiceRecord(uuid)
                socket.connect()
                runOnUiThread {
                    if (socket.isConnected) {
                        sharePref.writeString("name",name)
                        sharePref.writeString("address",address)
                        Toast.makeText(this, "Successfully Connected...", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,ChatActivity::class.java))
                    } else {
                        Toast.makeText(this, "Connection issue...", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Handle the exception
            }
        }.start()
    }
    companion object
    {
        const val PREFS_TOKEN_FILE="p"
    }
}