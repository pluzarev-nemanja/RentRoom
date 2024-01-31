package com.example.rentproject.data.data_source.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room

data class RoomAndPerson(
    @Embedded val room: Room,
    @Relation(
        parentColumn = "roomId",
        entityColumn = "roomId"
    )
    val person : Person?
)