package com.mikepm.letterrush.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.GameInfo
import com.mikepm.letterrush.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var searchText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.games),
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


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                SearchBar { text ->
                    searchText = text
                }
                GameList(searchText)
            }

            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.CreateGameScreen.route) {
                        popUpTo(Screen.GamesScreen.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun GameList(id: String) {
    val games = remember {
        arrayListOf(
            GameInfo("cities", 2, 4, "open", "1"),
            GameInfo("animals", 1, 4, "private", "2"),
            GameInfo("cities", 3, 4, "open", "3"),
            GameInfo("food", 4, 4, "full", "4"),
            GameInfo("animals", 2, 4, "open", "5")
        )
    }

    val idString = id.toString()

    // Filter the games where the game id contains the given id digits
    val filteredGames = games.filter { it.id.contains(idString) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(filteredGames) { game ->
            GameCard(
                type = game.type,
                playersIn = game.playersIn,
                maxPlayers = game.maxPlayers,
                status = game.status,
                id = game.id
            )
        }
    }
}

@Composable
fun GameCard(type: String, playersIn: Int, maxPlayers: Int, status: String, id: String) {
    val backgroundColor = when(status) {
        "open" -> Color.Green.copy(alpha = 0.2f)
        "private" -> Color(0xFFFFA500).copy(alpha = 0.2f)
        else -> Color.Red.copy(alpha = 0.2f)
    }

    val borderColor = when(status) {
        "open" -> Color.Green
        "private" -> Color(0xFFFFA500)
        else -> Color.Red
    }

    val image = when(type) {
        "cities" -> R.drawable.moscow
        "food" -> R.drawable.food
        "animals" -> R.drawable.capybara
        else -> R.drawable.globe
    }

    val status_lang = when(status) {
        "open" -> stringResource(R.string.open)
        "private" -> stringResource(R.string.private_)
        else -> stringResource(R.string.full)
    }

    val type_lang = when(type) {
        "cities" -> stringResource(id = R.string.cities)
        "animals" -> stringResource(id = R.string.animals)
        "food" -> stringResource(id = R.string.food)
        else -> "robot"
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Black,
        ),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(95.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(95.dp)
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .height(80.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = type_lang, style = MaterialTheme.typography.titleMedium)
                Text(text = "ID: $id", style = MaterialTheme.typography.bodyMedium)
                Text(text = stringResource(R.string.players, playersIn, maxPlayers), style = MaterialTheme.typography.bodyMedium)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundColor)
                    .border(1.dp, borderColor, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            ) {
                Text(text = status_lang, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun SearchBar(
    onFilterChanged: (String) -> Unit
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onFilterChanged(it)
            },
            shape = MaterialTheme.shapes.medium,
            colors = colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                disabledTextColor = Color.White
            ),
            placeholder = { Text("Search games") },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.medium
                ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        // Add functionality for filter button if needed
                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.filter_alt_24px), contentDescription = null)
                }
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        )
    }
}