package com.example.rentproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Room(
    @PrimaryKey val roomId : Int,
    val available : Boolean,
    val rent : Float,
    val numberOfPeople : Int,
    val maxNumberOfPeople : Int,
    val roomName : String,
    val reservationPeriod : Int,
    val totalIncome : Float,
    val currency: String

)
