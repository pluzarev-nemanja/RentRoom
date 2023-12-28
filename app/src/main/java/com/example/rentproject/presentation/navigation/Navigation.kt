package com.example.rentproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rentproject.domain.model.Room
import com.example.rentproject.presentation.home.HomeScreen
import com.example.rentproject.presentation.room_details.RoomDetailsScreen
import com.example.rentproject.presentation.settings.SettingsScreen
import com.example.rentproject.presentation.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    allRooms: List<Room>
) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                allRooms = allRooms,
                navController = navController
            )
        }

        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen()
        }

        composable(route = Screen.RoomDetailsScreen.route) {
            RoomDetailsScreen()
        }
    }
}