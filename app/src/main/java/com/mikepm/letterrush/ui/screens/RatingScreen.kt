package com.mikepm.letterrush.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.PlayerRatingInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingScreen() {

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.rating),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        val items = listOf(PlayerRatingInfo(1, R.drawable.moscow,"Elena", 360), PlayerRatingInfo(2, R.drawable.moscow,"Elena2", 380))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(Color.Black)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        PlayerCard(place = item.place, image = item.image, username = item.username, score = item.score)
                    }
                }
//                HorizontalDivider()
//                PlayerCard(place = 102, image = R.drawable.food, username = "Mike", score = 108)
            }
        }
    }
}

@Composable
fun PlayerCard(place: Int, image: Int, username: String, score: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = place.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = username,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$score pts",
                fontWeight = FontWeight.Bold
            )
        }
    }
}