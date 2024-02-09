package com.example.rentproject.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.rentproject.domain.model.Room
import com.example.rentproject.presentation.navigation.Navigation
import com.example.rentproject.presentation.rooms.RoomsViewModel

@Composable
fun MainScreen(
    darkTheme: Boolean,
    onThemeUpdated : () -> Unit
) {

    val navController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Navigation(
            navController = navController,
            darkTheme = darkTheme,
            onThemeUpdated = onThemeUpdated
        )
    }

}