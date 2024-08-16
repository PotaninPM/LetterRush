package com.mikepm.letterrush.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mikepm.letterrush.ui.screens.AccountScreen
import com.mikepm.letterrush.ui.screens.CreateGameScreen
import com.mikepm.letterrush.ui.screens.GamesScreen
import com.mikepm.letterrush.ui.screens.HomeScreen
import com.mikepm.letterrush.ui.screens.LobbyScreen
import com.mikepm.letterrush.ui.screens.MainScreen
import com.mikepm.letterrush.ui.screens.RatingScreen

@Composable
fun WordconNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screen.MainScreen.route) {
            MainScreen()
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.GamesScreen.route) {
            GamesScreen(navController = navController)
        }
        composable(Screen.RatingScreen.route) {
            RatingScreen()
        }
        composable(Screen.AccountScreen.route) {
            AccountScreen()
        }
        composable("${Screen.LobbyScreen.route}/{lobbyId}") { backStackEntry ->
            val lobbyId = backStackEntry.arguments?.getString("lobbyId")
            LobbyScreen(
                lobbyId = lobbyId.toString(),
                navController = navController
            )
        }
        
        composable(Screen.CreateGameScreen.route) {
            CreateGameScreen(navController = navController)
        }
    }
}