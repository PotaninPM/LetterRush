package com.mikepm.letterrush.core.network.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class GameCategory(
    val id: Long,
    val type: String,
    @StringRes val name: Int,
    @DrawableRes val image: Int
)
