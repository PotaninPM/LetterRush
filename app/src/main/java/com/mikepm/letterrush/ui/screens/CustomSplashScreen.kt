package com.mikepm.letterrush.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepm.letterrush.R
import kotlinx.coroutines.delay

@Composable
fun CustomSplashScreen(
    onTimeout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.wordconlogo),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Загрузка...",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )
        }
    }

    LaunchedEffect(key1 = true) {
        delay(3000)
        onTimeout()
    }
}
