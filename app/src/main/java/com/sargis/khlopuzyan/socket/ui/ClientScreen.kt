package com.sargis.khlopuzyan.socket.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sargis.khlopuzyan.socket.ui.theme.SocketTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

@Composable
fun ClientScreen(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()

    var recText by remember {
        mutableStateOf("No message from server")
    }

    var serverIp by remember {
        mutableStateOf("192.168.10.17")
    }

    var serverPort by remember {
        mutableStateOf("1234")
    }

    val pattern = remember { Regex("^\\d+\$") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Client",
            fontSize = 34.sp
        )

        Text(
            text = recText,
            fontSize = 18.sp
        )

        TextField(
            value = serverIp,
            placeholder = {
                Text(text = "Server IP")
            },
            onValueChange = {
                serverIp = it
            }
        )

        TextField(
            value = serverPort,
            placeholder = {
                Text(text = "Server Port")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    serverPort = it
                }
            }
        )

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    connectToServer(
                        serverName = serverIp,
                        serverPort = serverPort.toInt(),
                        callback = { recTime ->
                            recText = recTime
                        }
                    )
                }
            }
        ) {
            Text(
                text = "Connect to server".uppercase(),
                fontSize = 24.sp,
            )
        }
    }
}

private fun connectToServer(serverName: String, serverPort: Int, callback: (String) -> Unit) {
    try {
        val socket = Socket(serverName, serverPort)
        val bufferedReader = BufferedReader(InputStreamReader(socket.inputStream))
        val text = bufferedReader.readLine()
        callback(text)
//        socket.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun ClientScreenPreview() {
    SocketTheme {
        ClientScreen(modifier = Modifier.fillMaxSize())
    }
}