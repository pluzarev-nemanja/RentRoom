package com.example.rentproject.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.BedroomParent
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.BedroomParent
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.ui.graphics.vector.ImageVector

enum class RoomTabs(
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val text : String
) {
    GroundFloor(
        selectedIcon = Icons.Filled.Chair,
        unselectedIcon = Icons.Outlined.Chair,
        text = "Ground Floor"
    ),
    FirstFloor(
    selectedIcon = Icons.Filled.Bed,
    unselectedIcon = Icons.Outlined.Bed,
    text = "First Floor"
    ),
    SecondFloor(
    selectedIcon = Icons.Filled.BedroomParent,
    unselectedIcon = Icons.Outlined.BedroomParent,
    text = "Second Floor"
    )
}