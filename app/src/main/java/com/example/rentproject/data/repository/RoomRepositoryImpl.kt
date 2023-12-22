package com.example.rentproject.data.repository

import com.example.rentproject.data.data_source.RoomDao
import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class RoomRepositoryImpl(
    private val roomDao: RoomDao
) : RoomRepository {
    override fun getRooms(): Flow<List<Room>> {
        return roomDao.getRooms()
    }

    override suspend fun getRoomById(id: Int): Room? {
        return  roomDao.getRoomById(id)
    }

    override suspend fun upsertRoom(room: Room) {
        roomDao.upsertRoom(room)
    }

    override suspend fun deleteRoom(room: Room) {
        roomDao.deleteRoom(room)
    }
}