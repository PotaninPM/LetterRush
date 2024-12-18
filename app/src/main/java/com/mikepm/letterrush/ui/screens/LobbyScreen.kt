package com.mikepm.letterrush.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.MessageSender
import com.mikepm.letterrush.core.network.entities.Participant
import kotlin.random.Random

fun getRandomColor(): Color {
    val channels = listOf(0f, 0f, 0f).toMutableList()

    channels[Random.nextInt(3)] = 1.0f

    for(i in channels.indices) {
        if (channels[i] == 0f) {
            channels[i] = Random.nextFloat() * 0.5f + 0.5f
        }
    }

    return Color(
        red = channels[0],
        green = channels[1],
        blue = channels[2]
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(
    lobbyId: String,
    navController: NavController
) {
    var messages by rememberSaveable {
        mutableStateOf(
            arrayListOf(
                MessageSender("Alice", "Боб"),
                MessageSender("Bob", "Арбуз"),
                MessageSender("Bob", "Арбуз"),
                MessageSender("Bob", "Арбуз")
            )
        )
    }

    val userColors = rememberSaveable { mutableMapOf<String, Color>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.go_back)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions =  {
                    IconButton(onClick = {  }) {
                        Icon(painter = painterResource(id = R.drawable.menu_book_24px), contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets(top = 10.dp))
        ) {
            val participants = arrayListOf(
                Participant(1, "Вы", R.drawable.moscow),
                Participant(2, "не гений", R.drawable.food),
                Participant(3, "гений", R.drawable.moscow),
                Participant(4, "nickname", R.drawable.food)
            )
            Spacer(modifier = Modifier.height(4.dp))
            AllParticipantsList(participants)
            CurrentChat(
                messages = messages,
                userColors = userColors,
                modifier = Modifier
                    .weight(15f)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            MessageInput(
                onSendMessage = { message ->
                    messages = ArrayList(messages).apply { add(message) }
                }
            )
        }
    }
}

@Composable
fun CurrentChat(messages: ArrayList<MessageSender>, userColors: MutableMap<String, Color>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
            ,
            reverseLayout = true,
        ) {
            items(messages.reversed()) { message ->
                val color = userColors.getOrPut(message.username) { getRandomColor() }
                MessageItem(messageText = message.messageText, username = message.username, usernameColor = color)
            }
        }

        LaunchedEffect(key1 = messages.size) {
            listState.scrollToItem(0)
        }
    }
}

@Composable
fun MessageItem(
    username: String,
    messageText: String,
    usernameColor: Color
) {
    Card(
        modifier = Modifier
            .padding(start = 24.dp, top = 6.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .border(1.dp, Color.White, shape = MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(text = username, color = usernameColor)
            Text(text = messageText)
        }
    }
}

@Composable
fun MessageInput(onSendMessage: (MessageSender) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        leadingIcon = {
            IconButton(onClick = {

            }) {
                Icon(painter = painterResource(id = R.drawable.chat_24px), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                onSendMessage(MessageSender("Вы", text))
                text = ""
            }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.primary)
            }
        },
        value = text,
        onValueChange = {text = it},
        shape = MaterialTheme.shapes.medium,
        placeholder = { Text(stringResource(R.string.type_a_word)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.Black,
            errorContainerColor = Color.Transparent,
            cursorColor = Color.White,
            disabledTextColor = Color.White
        )
    )
}

@Composable
fun AllParticipantsList(participants: ArrayList<Participant>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(participants) { participant ->
            ParticipantCard(participant)
        }
    }
}

@Composable
fun NextMode() {
    var dialog by rememberSaveable {
        mutableStateOf(true)
    }
    
    if(dialog) {
        Dialog(onDismissRequest = { dialog = !dialog }) {

        }
    }
}

@Composable
fun ParticipantCard(participant: Participant) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.moscow),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(shape = MaterialTheme.shapes.large)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
        )
        Text(text = participant.name, color = Color.White)
    }
}