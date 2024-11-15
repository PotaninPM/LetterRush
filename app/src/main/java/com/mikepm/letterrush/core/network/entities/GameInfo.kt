package com.mikepm.letterrush.core.network.entities

import com.google.gson.annotations.SerializedName

data class GameInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("roomName") val roomName: String,
    @SerializedName("isPrivate") val isPrivate: Boolean,
    @SerializedName("isRated") val isRated: Boolean,
    @SerializedName("mode") val mode: String,
    @SerializedName("playerLimit") val playerLimit: Int,
    @SerializedName("currentPlayers") val currentPlayers: Int,
    @SerializedName("turnDurationSec") val turnDurationSec: Int
)