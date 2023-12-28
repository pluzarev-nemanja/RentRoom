package com.example.rentproject.presentation.util

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingsItems(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route : String
)