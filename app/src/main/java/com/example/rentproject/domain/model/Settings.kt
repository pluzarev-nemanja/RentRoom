package com.example.rentproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = false) val id : Int = 0,
    val darkMode : Boolean,
    val currency : Boolean
)