package com.example.rentproject.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rentproject.domain.model.Room

@Database(
    entities = [Room::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDataBase : RoomDatabase() {
    abstract val roomDao : RoomDao

    companion object{
        const val DATABASE_NAME = "rooms_db"
    }
}