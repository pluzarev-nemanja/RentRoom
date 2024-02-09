package com.example.rentproject.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rentproject.domain.model.Floor
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.model.Settings

@Database(
    entities = [Room::class,Floor::class,Person::class,Settings::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDataBase : RoomDatabase() {
    abstract val roomDao : RoomDao

    companion object{
        const val DATABASE_NAME = "rooms_db"
    }
}