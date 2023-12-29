package com.example.rentproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rentproject.domain.model.Room
import com.example.rentproject.presentation.home.HomeScreen
import com.example.rentproject.presentation.room_details.RoomDetailsScreen
import com.example.rentproject.presentation.rooms.RoomsViewModel
import com.example.rentproject.presentation.settings.SettingsScreen
import com.example.rentproject.presentation.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    roomsViewModel: RoomsViewModel = hiltViewModel()
) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                allRooms = roomsViewModel.roomsList,
                saveNavigatedRoom = { room->
                    roomsViewModel.saveNavigatedRoom(room)
                },
                navController = navController
            )
        }

        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen()
        }

        composable(route = Screen.RoomDetailsScreen.route) {
            RoomDetailsScreen(
                room = roomsViewModel.navigatedRoom.value,
                navController = navController
            )
        }
    }
}