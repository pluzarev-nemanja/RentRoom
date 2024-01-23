package com.example.rentproject.domain.use_case

import com.example.rentproject.data.data_source.relations.FloorWithRooms
import com.example.rentproject.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class GetFloorWithRooms(
    private val roomRepository: RoomRepository
) {

    operator fun invoke(floorId: Int): Flow<List<FloorWithRooms>> {
        return roomRepository.getFloorWithRooms(floorId)
    }
}