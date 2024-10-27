package com.mikepm.letterrush.core.server

import com.google.gson.annotations.SerializedName

data class NewGameRequest(
    @SerializedName("roomName") val roomName: String,
    @SerializedName("isPrivate") val isPrivate: Boolean,
    @SerializedName("password") val password: String,
    @SerializedName("isRated") val isRated: Boolean,
    @SerializedName("mode") val mode: String,
    @SerializedName("playerLimit") val playerLimit: Int,
    @SerializedName("currentPlayers") val currentPlayers: Int,
    @SerializedName("turnDurationSec") val turnDurationSec: Int
)
