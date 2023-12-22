package com.example.rentproject.domain.model

import androidx.room.Entity

@Entity
data class Room(
    val roomId : Int,
    val available : Boolean,
    val rent : Int,
    val numberOfPeople : Int,

)
