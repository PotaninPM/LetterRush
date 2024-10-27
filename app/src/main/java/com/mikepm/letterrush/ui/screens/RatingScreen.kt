package com.mikepm.letterrush.ui.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepm.letterrush.R
import com.mikepm.letterrush.ui.navigation.Screen

data class LeaderboardItem(
    val position: Int,
    val name: String,
    val points: Int,
    val imageUrl: Int
)

@Composable
fun RatingScreen() {
    val leaderboardItems = listOf(
        LeaderboardItem(1, "Bryahn", 400, R.drawable.moscow), // Replace with the actual image resource
        LeaderboardItem(2, "Meghan", 390, R.drawable.moscow),
        LeaderboardItem(3, "Alex", 380, R.drawable.moscow),
        LeaderboardItem(4, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(5, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(6, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(7, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(8, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(9, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(10, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(11, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(12, "Marsha Fisher", 360, R.drawable.moscow),
        LeaderboardItem(13, "Marsha Fisher", 360, R.drawable.moscow),
        // Add more items as needed
    )
    val userPosition = LeaderboardItem(101, "Вы", 30, R.drawable.moscow)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest) // Background color of the screen
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leaderboardItems.take(3).forEach { item ->
                LeaderboardTopItem(item)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(leaderboardItems.drop(3)) { item ->
                LeaderboardListItem(item = item)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        LeaderboardListItem(userPosition, isUser = true)
    }
}

@Composable
fun LeaderboardTopItem(item: LeaderboardItem) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            ) {
                Image(
                    painter = painterResource(id = item.imageUrl),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.position.toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }

            }

            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "${item.points}",
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun LeaderboardListItem(item: LeaderboardItem, isUser: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(if(isUser) 0.dp else 18.dp)
            .padding(vertical = 4.dp)
            .background(
                if (isUser) Color(0xFF3A80F7) else MaterialTheme.colorScheme.surfaceContainerLow,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.position.toString(),
            color = if (isUser) Color.White else MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(vertical = 14.dp)
        )

        Image(
            painter = painterResource(id = item.imageUrl),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row {
            Text(
                text = item.name,
                color = if(isUser) Color.White else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isUser) FontWeight.Bold else FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                fontWeight = if (isUser) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.width(50.dp),
                text = "${item.points}",
                color = if(isUser) Color.White else MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )
        }
    }
}
