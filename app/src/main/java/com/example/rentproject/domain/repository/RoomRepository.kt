package com.example.rentproject.domain.repository

import com.example.rentproject.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>

    suspend fun getRoomById(id: Int): Room?

    suspend fun upsertRoom(room: Room)

    suspend fun deleteRoom(room: Room)
    suspend fun insertRooms(roomsList: MutableList<Room>)

}