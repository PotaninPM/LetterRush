package com.mikepm.letterrush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mikepm.letterrush.ui.screens.MainScreen
import com.mikepm.letterrush.ui.theme.WordconTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordconTheme {
                MainScreen()
            }
        }
    }
}