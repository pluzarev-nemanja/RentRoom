package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Settings
import com.example.rentproject.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsById(
    private val roomRepository: RoomRepository
) {

    operator fun invoke(id: Int):Flow<Settings>{
        return  roomRepository.getSettings(id)
    }
}