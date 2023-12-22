package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.repository.RoomRepository

class UpsertRoom(
    private val roomRepository: RoomRepository
) {

    suspend operator fun invoke(room: Room){
        roomRepository.upsertRoom(room)
    }
}