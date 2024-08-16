package com.mikepm.letterrush.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.GameMode
import com.mikepm.letterrush.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameScreen(navController: NavController) {
    var playersLimit by rememberSaveable { mutableIntStateOf(2) }
    var roomName by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.create_game)) },
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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = stringResource(id = R.string.create_game))
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                },
                onClick = if (roomName.isNotBlank()) {
                    { navController.navigate("${Screen.LobbyScreen.route}/$roomName") }
                } else {
                    { null }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            CreateGameFormSection(titleRes = R.string.room_info) {
                RoomNameTextField(roomName) { newRoomName ->
                    roomName = newRoomName
                }
                RoomPasswordTextField()
                RankingImpactSwitch()
            }
            GameModeSection(titleRes = R.string.mode) {
                GameModeDropdownMenu()
            }
            PlayersLimitSection(titleRes = R.string.players_limit, playersLimit = playersLimit) {
                PlayersLimitSlider(playersLimit) { newLimit ->
                    playersLimit = newLimit
                }
            }
            TurnPlayersLimitSection(titleRes = R.string.turn_duration) {
                TurnPlayersLimit()
            }
        }
    }
}

@Composable
fun CreateGameFormSection(
    @StringRes titleRes: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 12.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun RoomNameTextField(
    roomName: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = roomName,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.room_name)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.medium
            ),
        colors = colors(
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = Color.White,
            disabledTextColor = Color.White
        )
    )
}

@Composable
fun RoomPasswordTextField() {
    var roomPassword by rememberSaveable { mutableStateOf("") }

    TextField(
        value = roomPassword,
        onValueChange = { roomPassword = it },
        label = { Text(stringResource(R.string.room_password)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = MaterialTheme.shapes.medium
            ),
        colors = colors(
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = Color.White,
            disabledTextColor = Color.White
        )
    )
}

@Composable
fun RankingImpactSwitch() {
    var isChecked by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.ranking_impact),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            thumbContent = if (isChecked) {
                {
                    Icon(
                        painter = painterResource(id = R.drawable.trophy_filled),
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}

@Composable
fun GameModeSection(
    @StringRes titleRes: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameModeDropdownMenu() {
    val categoriesList = listOf(
        GameMode(
            id = "cities",
            title = stringResource(id = R.string.cities),
            image = R.drawable.skyscraper
        ),
        GameMode(
            id = "food",
            title = stringResource(id = R.string.food),
            image = R.drawable.apple
        ),
        GameMode(
            id = "animals",
            title = stringResource(id = R.string.animals),
            image = R.drawable.sex_sex
        )
    )

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf(categoriesList[0].title) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        TextField(
            value = selectedCategory,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            colors = colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = Color.White,
                disabledTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.medium
                ),
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            categoriesList.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        selectedCategory = category.title
                        isExpanded = false
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = category.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(24.dp)
                            )
                            Text(
                                text = category.title,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PlayersLimitSection(
    @StringRes titleRes: Int,
    playersLimit: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = "${stringResource(id = titleRes)}: $playersLimit",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 12.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun PlayersLimitSlider(
    playersLimit: Int,
    onValueChange: (Int) -> Unit
) {
    Slider(
        value = playersLimit.toFloat(),
        onValueChange = { onValueChange(it.toInt()) },
        valueRange = 2f..50f,
        steps = 49,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
    )
}

@Composable
fun TurnPlayersLimitSection(
    @StringRes titleRes: Int,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 12.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnPlayersLimit() {
    val units = listOf(
        stringResource(id = R.string.seconds),
        stringResource(id = R.string.minutes),
        stringResource(id = R.string.hours),
        stringResource(id = R.string.days)
    )
    var value by rememberSaveable { mutableStateOf("") }
    var selectedUnit by rememberSaveable { mutableStateOf(units[0]) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            label = {
                Text(
                    text = stringResource(id = R.string.amount),
                )
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.medium
                ),
            colors = colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                cursorColor = Color.White,
                disabledTextColor = Color.White
            ),
        )
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        ) {
            TextField(
                value = selectedUnit,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = MaterialTheme.shapes.medium
                    ),
                colors = colors(
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    disabledTextColor = Color.White
                ),
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        onClick = {
                            selectedUnit = unit
                            isExpanded = false
                        },
                        text = { Text(unit) }
                    )
                }
            }
        }
    }
}