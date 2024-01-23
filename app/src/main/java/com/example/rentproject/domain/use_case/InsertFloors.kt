package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Floor
import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.repository.RoomRepository

class InsertFloors (
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(numberOfFloors: Int,floorName: String) {
        val floors = createFloors(numberOfFloors,floorName)
        roomRepository.insertFloors(floors)
    }

    private fun createFloors(numberOfFloors: Int,floorName : String) : MutableList<Floor>{
        val floorList = mutableListOf<Floor>()
        for(i: Int in 0 until numberOfFloors){
            floorList += Floor(
                floorId = i,
                floorName = floorName,
            )
        }
        return floorList
    }
}