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
        var roomNum = 1
        for(i: Int in 1..numberOfRooms){
            val floorId: Int = if(i in 4..14) 1
            else if (i < 4) 0
            else 2

            if(i == 4) roomNum = 1
            if(i == 15) roomNum = 1
            val roomName : String = when(i){
                in 0..3 -> "D $roomNum"
                in 4..14 -> "S $roomNum"
                else -> "P $roomNum"
            }
            roomList +=Room(
                roomId = i,
                available = true,
                rent =  0F,
                roomName = roomName,
                reservationPeriod = 0,
                totalIncome = 0F,
                floorId = floorId
            )
            roomNum++
        }
        return roomList
    }
}