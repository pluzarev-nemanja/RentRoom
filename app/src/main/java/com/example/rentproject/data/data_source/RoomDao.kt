package com.example.rentproject.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.rentproject.domain.model.Room
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT * FROM room ORDER BY roomId ASC")
    fun getRooms(): Flow<List<Room>>

    @Query("SELECT * FROM room WHERE roomId = :id")
    suspend fun getRoomById(id : Int) : Room?

    @Upsert
    suspend fun upsertRoom(room: Room)

    @Delete
    suspend fun deleteRoom(room: Room)

}