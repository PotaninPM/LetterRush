package com.mikepm.letterrush.ui.screens

import android.health.connect.datatypes.HeightRecord
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.GameInfo
import com.mikepm.letterrush.core.server.fetchGames
import com.mikepm.letterrush.ui.navigation.Screen
import kotlinx.coroutines.launch
import java.util.logging.Filter

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
                .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
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
    var games by rememberSaveable {
        mutableStateOf<List<GameInfo>>(emptyList())
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val fetchedGames = fetchGames()
                if(id != "") {
                    games = fetchedGames.filter { game -> game.id.toString() == id }
                } else {
                    games = fetchedGames
                }
            } catch (e: Exception) {
                Log.e("Error", "Failed to fetch games: ${e.message}")
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(games) { game ->
            val status = when(game.isPrivate) {
                true -> "private"
                false -> {
                    if(game.playerLimit == game.currentPlayers) {
                        "full"
                    } else {
                        "open"
                    }
                }
            }

            GameCard(
                type = game.mode,
                playersIn = game.currentPlayers,
                maxPlayers = game.playerLimit,
                status = status,
                id = game.id.toString()
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
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .fillMaxWidth()
            .shadow(15.dp)
            .height(95.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
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

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(backgroundColor)
                        .align(Alignment.CenterVertically)
                        .border(1.dp, borderColor, shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                ) {
                    Text(text = status_lang, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun FilterDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onFilterSelected: (List<String>) -> Unit
) {
    val selectedFilters = remember { mutableStateListOf<String>() }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        val filters = listOf("Filter 1", "Filter 2", "Filter 3")
        filters.forEach {
            FilterChip(it)
        }
    }
}

@Composable
fun FilterChip(
    type: String
) {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text("Filter chip")
        },
        selected = selected,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

@Composable
fun SearchBar(
    onFilterChanged: (String) -> Unit
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }
    var showFilterDialog by remember { mutableStateOf(false) }

    if (showFilterDialog) {
        FilterDropdownMenu(expanded = showFilterDialog, onDismissRequest = { showFilterDialog }) {
            
        }
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
                        showFilterDialog = true
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