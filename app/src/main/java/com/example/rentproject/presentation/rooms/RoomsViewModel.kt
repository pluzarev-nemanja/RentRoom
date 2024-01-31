package com.example.rentproject.presentation.rooms

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentproject.data.data_source.relations.FloorWithRooms
import com.example.rentproject.data.data_source.relations.RoomAndPerson
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.use_case.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomUseCases: RoomUseCases
) : ViewModel() {

    var room = mutableStateOf<Room?>(null)
    var navigatedRoom = mutableStateOf<Room?>(null)
    var groundFloor = mutableStateListOf<FloorWithRooms>()
    var firstFloor = mutableStateListOf<FloorWithRooms>()
    var secondFloor = mutableStateListOf<FloorWithRooms>()
    var roomAndPerson = mutableStateListOf<RoomAndPerson>()

    init {
        insertRooms(19)
        viewModelScope.launch {
            roomUseCases.insertFloors(3,"Floor")
        }
        viewModelScope.launch {
            roomUseCases.getFloorWithRooms(0).collect{ floorWithRooms->
                groundFloor.clear()
                groundFloor += floorWithRooms
            }
        }
        viewModelScope.launch {

            roomUseCases.getFloorWithRooms(1).collect{ floorWithRooms->
                firstFloor.clear()
                firstFloor += floorWithRooms

            }
        }
        viewModelScope.launch {
            roomUseCases.getFloorWithRooms(2).collect{ floorWithRooms->
                secondFloor.clear()
                secondFloor += floorWithRooms

            }
        }

    }

    private fun insertRooms(numberOfRooms : Int){
        viewModelScope.launch {
            roomUseCases.insertRooms(numberOfRooms = numberOfRooms)
        }
    }
    fun saveNavigatedRoom(room: Room?){
        navigatedRoom.value = room
    }
    fun deleteRoom(room: Room){
        viewModelScope.launch {
            roomUseCases.deleteRoom(room)
        }
    }

    fun upsertRoom(room: Room){
        viewModelScope.launch {
            roomUseCases.upsertRoom(room)
        }
    }
    fun getRoomById(id: Int):Room? {
        viewModelScope.launch{
            room.value = roomUseCases.getRoomById(id)
        }
        return room.value
    }

    fun upsertPerson(person: Person){
        viewModelScope.launch {
            roomUseCases.upsertPerson(person)
        }
    }

    fun deletePerson(person: Person){
        viewModelScope.launch {
            roomUseCases.deletePerson(person)
        }
    }

    fun getRoomAndPerson(roomId : Int){
        viewModelScope.launch {
            roomUseCases.getRoomAndPerson(roomId).collect{
                roomAndPerson.clear()
                roomAndPerson += it
            }
        }
    }
}