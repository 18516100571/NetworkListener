package com.wallet.allapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wallet.library.Constants.LOG_TAG
import com.wallet.library.annotation.Network
import com.wallet.library.annotation.NetworkManager
import com.wallet.library.type.NetType


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkManager.getDefault().register(this)


    }

    @Network(netType = NetType.WIFI)
    fun changeNetwork(netType: NetType) {

        when (netType) {
            NetType.WIFI -> {
                Log.e(LOG_TAG, "wifi链接")
            }
            NetType.CMNET -> {
                Log.e(LOG_TAG, "CMNET链接")
            }
            NetType.CMWAP -> {
                Log.e(LOG_TAG, "CMWAP链接")
            }
            NetType.NONE -> {
                Log.e(LOG_TAG, "NONE链接")
            }
            NetType.AUTO -> {
                Log.e(LOG_TAG, "AUTO链接")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.getDefault().unRegister(this)
    }
}
