package com.sargis.khlopuzyan.server

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sargis.khlopuzyan.server.ui.ServerScreen
import com.sargis.khlopuzyan.server.ui.theme.SocketTheme

class MainServerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocketTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ServerScreen(
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