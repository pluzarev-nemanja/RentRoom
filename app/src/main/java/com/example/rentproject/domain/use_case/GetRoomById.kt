package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.repository.RoomRepository

class GetRoomById(
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(id:Int):Room?{
        return roomRepository.getRoomById(id)
    }
}