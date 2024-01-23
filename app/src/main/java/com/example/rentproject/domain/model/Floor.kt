package com.example.rentproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Floor(
    @PrimaryKey val floorId : Int,
    val floorName : String,
)
