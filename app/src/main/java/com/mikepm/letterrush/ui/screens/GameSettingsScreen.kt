package com.mikepm.letterrush.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.GameCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSettingsScreen(navController: NavController) {

    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            BottomBar()
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Настройка игры") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            GameTypesSection(R.string.choose_mode) {
                GameTypes()
            }
        }
    }
}

@Composable
fun GameTypesSection(
    @StringRes titleRes: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .paddingFromBaseline(top = 10.dp, bottom = 6.dp)
                .padding(horizontal = 13.dp)
        )
        content()
    }
}

@Composable
fun BottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
            .padding(horizontal = 16.dp)
            .padding(bottom = 15.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFA7D36F),
                        Color(0xFF7DC260)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFF679F48),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = stringResource(R.string.play),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun GameTypes() {

    val types = listOf(
        GameCategory(
            id = 0,
            type = "random",
            name = R.string.random,
            image = R.drawable.randomgame
        ),
        GameCategory(
            id = 1,
            type = "cities",
            name = R.string.cities,
            image = R.drawable.moscow
        ),
        GameCategory(
            id = 2,
            type = "animals",
            name = R.string.animals,
            image = R.drawable.capybara
        ),
        GameCategory(
            id = 3,
            type = "food",
            name = R.string.food,
            image = R.drawable.food
        )
    )

    val selectedPageIndex = rememberSaveable { mutableStateOf(0) }

    LazyRow {
        items(types) { item ->
            Box(
                modifier = Modifier
                    .padding(9.dp)
                    .size(200.dp)
                    .clickable {
                        selectedPageIndex.value = item.id.toInt()
                    }
                    .then(
                        if (selectedPageIndex.value == item.id.toInt()) {
                            Modifier.border(
                                width = 4.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(16.dp)
                            )
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.image)
                            .setParameter("compression", "80")
                            .placeholder(R.drawable.placeholder_gradient)
                            .scale(Scale.FILL)
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .graphicsLayer(alpha = 0.8f)
                )
                Text(
                    text = stringResource(id = item.name),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                        )
                        .padding(14.dp)
                )
            }
        }
    }
}