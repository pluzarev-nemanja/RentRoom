package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class GetRooms(
    private val roomRepository : RoomRepository
) {

    operator fun invoke() : Flow<List<Room>>{
        return roomRepository.getRooms()
    }
}