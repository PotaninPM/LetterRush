package com.mikepm.letterrush.ui.navigation

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object CustomSplashScreen : Screen("splash")

    data object HomeScreen : Screen("home_screen")
    data object GamesScreen : Screen("games_screen")
    data object RatingScreen : Screen("rating_screen")
    data object AccountScreen : Screen("account_screen")

    data object LobbyScreen : Screen("lobby_screen")
    data object CreateGameScreen: Screen("create_game_screen")
    data object GameSettingsScreen: Screen("game_settings_screen")
}