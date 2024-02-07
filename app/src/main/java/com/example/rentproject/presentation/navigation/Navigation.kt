package com.example.rentproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room
import com.example.rentproject.presentation.home.HomeScreen
import com.example.rentproject.presentation.room_details.RoomDetailsScreen
import com.example.rentproject.presentation.rooms.RoomsViewModel
import com.example.rentproject.presentation.settings.SettingsScreen
import com.example.rentproject.presentation.util.ScaleTransitionDirection
import com.example.rentproject.presentation.util.Screen
import com.example.rentproject.presentation.util.scaleIntoContainer
import com.example.rentproject.presentation.util.scaleOutOfContainer

@Composable
fun Navigation(
    navController: NavHostController,
    roomsViewModel: RoomsViewModel = hiltViewModel()
) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        composable(
            route = Screen.HomeScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            HomeScreen(
                groundFloor = roomsViewModel.groundFloor,
                firstFloor = roomsViewModel.firstFloor,
                secondFloor = roomsViewModel.secondFloor,
                saveNavigatedRoom = { room->
                    roomsViewModel.saveNavigatedRoom(room)
                    roomsViewModel.getRoomAndPerson(room!!.roomId)
                },
                navController = navController
            )
        }

        composable(
            route = Screen.SettingsScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            SettingsScreen()
        }

        composable(
            route = Screen.RoomDetailsScreen.route,
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }
        ) {
            RoomDetailsScreen(
                room = roomsViewModel.navigatedRoom.value,
                navController = navController,
                roomAndPerson = roomsViewModel.roomAndPerson,
                upsertPerson = {
                    roomsViewModel.upsertPerson(it)
                },
                upsertRoom = {
                    roomsViewModel.upsertRoom(it)
                },
                deletePerson = {
                    roomsViewModel.deletePerson(it)
                }
            )
        }
    }
}