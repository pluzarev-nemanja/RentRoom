package com.example.rentproject.data.data_source.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rentproject.domain.model.Floor
import com.example.rentproject.domain.model.Room

data class FloorWithRooms (
    @Embedded val floor: Floor,
    @Relation(
        parentColumn = "floorId",
        entityColumn = "floorId"
    )
    val rooms : List<Room>
)