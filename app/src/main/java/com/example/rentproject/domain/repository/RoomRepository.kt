package com.example.rentproject.domain.repository

import com.example.rentproject.data.data_source.relations.FloorWithRooms
import com.example.rentproject.data.data_source.relations.RoomAndPerson
import com.example.rentproject.domain.model.Floor
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getRooms(): Flow<List<Room>>

    suspend fun getRoomById(id: Int): Room?

    suspend fun upsertRoom(room: Room)

    suspend fun deleteRoom(room: Room)
    suspend fun insertRooms(roomsList: MutableList<Room>)
    fun getFloorWithRooms(floorId: Int): Flow<List<FloorWithRooms>>
    suspend fun insertFloors(floors: List<Floor>)
    suspend fun upsertPerson(person: Person)

    suspend fun deletePerson(person: Person)
    fun getRoomAndPersonWithRoomId(roomId : Int): Flow<List<RoomAndPerson>>
    suspend fun upsertSettings(settings: Settings)
    fun getSettings(id: Int): Flow<Settings>

}