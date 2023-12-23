package com.example.rentproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Room(
    @PrimaryKey val roomId : Int,
    val available : Boolean,
    val rent : Int,
    val numberOfPeople : Int,

)
