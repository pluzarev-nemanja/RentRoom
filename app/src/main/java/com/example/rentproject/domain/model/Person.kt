package com.example.rentproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    val personId : Int,
    val personName : String,
    val personsLastName : String,
    val phoneNumber : Int,
    val gender : Boolean,
    @PrimaryKey(autoGenerate = false) val roomId: Int
)
