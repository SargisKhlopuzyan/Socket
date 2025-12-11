package com.sargis.khlopuzyan.socket.server

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

const val serverName = "time.nist.gov"
const val serverPort = 13

class NistTimeClient(
    private val serverName: String,
    private val serverPort: Int,
    private val callback: (String) -> Unit,
) : Runnable {

    override fun run() {
        try {
            val socket = Socket(serverName, serverPort)
            val bufferedReader = BufferedReader(InputStreamReader(socket.inputStream))
            bufferedReader.readLine() // This is empty line
            val recTime = bufferedReader.readLine().substring(6, 23)
//            bufferedReader.close() // ?
            callback(recTime)
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}