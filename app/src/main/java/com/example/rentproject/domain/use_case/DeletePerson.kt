package com.example.rentproject.domain.use_case

import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.repository.RoomRepository

class DeletePerson(
    private val repository: RoomRepository
) {

    suspend operator fun invoke(person: Person){
        repository.deletePerson(person)
    }
}