package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.repository.RoomRepository

class InsertRooms(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(numberOfRooms: Int) {
        val rooms = createRooms(numberOfRooms)
        roomRepository.insertRooms(rooms)
    }

    private fun createRooms(numberOfRooms: Int) : MutableList<Room>{
        val roomList = mutableListOf<Room>()
        for(i: Int in 1..numberOfRooms){
            roomList +=Room(
                roomId = i,
                available = true,
                rent =  0F,
                numberOfPeople = 0,
                maxNumberOfPeople = 0,
                roomName = "Room $i",
                reservationPeriod = 0,
                totalIncome = 0F,
                currency = "rsd"
            )
        }
        return roomList
    }
}