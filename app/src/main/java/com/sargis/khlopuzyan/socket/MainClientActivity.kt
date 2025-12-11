package com.sargis.khlopuzyan.socket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sargis.khlopuzyan.socket.ui.ClientScreen
import com.sargis.khlopuzyan.socket.ui.theme.SocketTheme

class MainClientActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocketTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
//                    SimpleSocketScreen(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(innerPadding)
//                    )

                    ClientScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}