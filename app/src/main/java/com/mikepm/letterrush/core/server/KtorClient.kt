package com.mikepm.letterrush.core.server

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mikepm.letterrush.core.network.entities.GameInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

val gson = Gson()

val client = HttpClient {
    install(ContentNegotiation) {
        gson
    }
}

suspend fun fetchGames() : List<GameInfo> {
    return withContext(Dispatchers.IO) {
        try {
            val response: String = client.get("http://172.16.146.231:8080/games").bodyAsText()
            val gameInfoType = object : TypeToken<List<GameInfo>>() {}.type
            gson.fromJson(response, gameInfoType)
        } catch (e: Exception) {
            Log.e("INFOG", "Failed to fetch games", e)
            emptyList()
        }
    }
}


suspend fun createNewGame(newGame: NewGameRequest): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse = client.post("http://172.16.146.231:8080/games/add") {
                contentType(ContentType.Application.Json)
                setBody(gson.toJson(newGame))
                Log.i("INFOG", gson.toJson(newGame))
            }
            response.status.value in 200..299
        } catch (e: Exception) {
            Log.e("INFOG", "Failed to create game ${e.message}")
            false
        }
    }
}