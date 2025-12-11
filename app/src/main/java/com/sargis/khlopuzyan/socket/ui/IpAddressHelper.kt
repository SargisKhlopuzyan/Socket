package com.sargis.khlopuzyan.socket.ui

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter


fun Context.getIpAddress() : String {
    val context: Context = this.applicationContext
    val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    return Formatter.formatIpAddress(wm.connectionInfo.getIpAddress())
}