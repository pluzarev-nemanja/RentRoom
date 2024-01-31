package com.example.rentproject.domain.use_case

import com.example.rentproject.data.data_source.relations.RoomAndPerson
import com.example.rentproject.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class GetRoomAndPerson(
    private val roomRepository: RoomRepository
) {

    operator fun invoke(roomId : Int): Flow<List<RoomAndPerson>> {
        return roomRepository.getRoomAndPersonWithRoomId(roomId)
    }
}