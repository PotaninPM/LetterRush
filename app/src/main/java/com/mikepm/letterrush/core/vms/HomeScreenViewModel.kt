package com.mikepm.letterrush.core.vms

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikepm.letterrush.R
import com.mikepm.letterrush.core.network.entities.GameCategory
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val _categories = mutableStateListOf<GameCategory>()
    val categories = _categories as List<GameCategory>
    private var isInitialized = false

    fun fetchCategories() {
        viewModelScope.launch {
            if(!isInitialized) {
                val categoriesList = listOf(
                    // stub data
                    GameCategory(
                        id = 0,
                        type = "robot",
                        name = R.string.with_robot,
                        image = R.drawable.robot
                    ),
                    GameCategory(
                        id = 1,
                        type = "one",
                        name = R.string.onevsone,
                        image = R.drawable.onevsone
                    ),
                    GameCategory(
                        id = 2,
                        type = "group",
                        name = R.string.group,
                        image = R.drawable.groupgame
                    )
                )
                _categories.addAll(categoriesList)
                isInitialized = true
            }
        }
    }
}