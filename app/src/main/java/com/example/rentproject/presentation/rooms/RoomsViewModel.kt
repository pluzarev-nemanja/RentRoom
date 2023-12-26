package com.example.rentproject.presentation.rooms

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var roomsList = mutableStateListOf<Room>()
    var room = mutableStateOf<Room?>(null)

    init {
        viewModelScope.launch {
            roomUseCases.insertRooms(5)
        }
        viewModelScope.launch {
            roomUseCases.getRooms().collect{rooms->
                roomsList.clear()
                roomsList +=rooms
            }
        }
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
}