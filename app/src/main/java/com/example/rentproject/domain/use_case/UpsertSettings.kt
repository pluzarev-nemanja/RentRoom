package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Settings
import com.example.rentproject.domain.repository.RoomRepository

class UpsertSettings(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(settings: Settings){
        roomRepository.upsertSettings(settings)
    }
}