package com.example.rentproject.presentation.util

sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("home_screen")
    object RoomDetailsScreen : Screen("room_details_screen")
    object SettingsScreen : Screen("settings_screen")

}