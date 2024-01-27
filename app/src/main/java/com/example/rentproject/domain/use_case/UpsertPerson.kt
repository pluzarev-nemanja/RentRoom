package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.repository.RoomRepository

class UpsertPerson(
    private val repository: RoomRepository
) {
    suspend operator fun invoke(person: Person){
        repository.upsertPerson(person)
    }
}