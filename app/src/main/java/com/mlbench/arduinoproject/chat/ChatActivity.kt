package com.mlbench.arduinoproject.chat

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

import com.mlbench.arduinoproject.databinding.ActivityChatBinding
import java.io.IOException

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // initView()
    }

    /*private fun initView() {
        readBlueToothDataFromMothership()
    }*/
   /* private fun readBlueToothDataFromMothership(bluetoothSocket: BluetoothSocket) {
        val bluetoothSocketInputStream = bluetoothSocket.inputStream
        val buffer = ByteArray(1024)
        var bytes: Int
        //Loop to listen for received bluetooth messages
        while (true) {
            try {
                bytes = bluetoothSocketInputStream.read(buffer)
                val readMessage = String(buffer, 0, bytes)
                //liveData.postValue(readMessage)
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }*/

    // display or don't star image
    private fun View.showOrHideImage(imageShow: Boolean) {
        visibility = if (imageShow) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}