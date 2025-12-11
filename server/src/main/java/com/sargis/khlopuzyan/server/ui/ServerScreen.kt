package com.sargis.khlopuzyan.server.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargis.khlopuzyan.server.getIpAddress
import com.sargis.khlopuzyan.server.ui.theme.SocketTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.PrintWriter
import java.net.ServerSocket

// check your own IP address in emulator or real device
//private const val serverIp = "192.168.10.17"

// choose your own port number, > 1023, avoid reserved ones, like 8080, etc
private const val serverPort = 1234
val server = Server()

@Composable
fun ServerScreen(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var connectionStatus by remember {
        mutableStateOf("No connection yet")
    }

    var serverIp by remember {
        mutableStateOf(context.getIpAddress())
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Server",
            fontSize = 34.sp
        )

        Text(
            text = "Server IP: $serverIp",
            fontSize = 18.sp
        )

        Text(
            text = "Server Port: $serverPort",
            fontSize = 18.sp
        )

        Text(
            text = connectionStatus,
            fontSize = 18.sp
        )

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    server.connectToServer(
                        serverPort = serverPort,
                        callback = { serverStatus ->
                            connectionStatus = serverStatus
                        }
                    )
                }
            }
        ) {
            Text(
                text = "Start server".uppercase(),
                fontSize = 24.sp,
            )
        }

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    server.stopServer { serverStatus ->
                        connectionStatus = serverStatus
                    }
                }
            }
        ) {
            Text(
                text = "Stop server".uppercase(),
                fontSize = 24.sp,
            )
        }
    }
}

class Server {

    var count = 0
    var serverRunning: Boolean = false
    var serverSocket: ServerSocket? = null

    fun connectToServer(
        serverPort: Int,
        callback: (String) -> Unit,
    ) {
        try {
            serverRunning = true
            serverSocket = ServerSocket(serverPort)
            callback("Waiting for clients")

            while (serverRunning) {
                val socket = serverSocket?.accept()
                count++

                callback("Connected to : ${socket?.inetAddress} : ${socket?.localPort}")

                val outputServer: PrintWriter = PrintWriter(socket?.outputStream)

                outputServer.write("Welcome to Server: $count")
                outputServer.flush()
                socket?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopServer(callback: (String) -> Unit) {
        serverRunning = false
        try {
            serverSocket?.close()
            callback("Server stopped")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServerScreenPreview() {
    SocketTheme {
        ServerScreen(modifier = Modifier.fillMaxSize())
    }
}