package com.example.rentproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Room(
    @PrimaryKey(autoGenerate = true)val roomId : Int,
    val available : Boolean,
    val rent : Float,
    val roomName : String,
    val reservationPeriod : Int,
    val totalIncome : Float,
    val currency: String,
    val floorId : Int
)
