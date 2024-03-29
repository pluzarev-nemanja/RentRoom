package com.example.rentproject.domain.use_case

data class RoomUseCases(
    val deleteRoom: DeleteRoom,
    val getRoomById: GetRoomById,
    val getRooms: GetRooms,
    val upsertRoom: UpsertRoom,
    val insertRooms: InsertRooms,
    val getFloorWithRooms: GetFloorWithRooms,
    val insertFloors: InsertFloors,
    val upsertPerson: UpsertPerson,
    val deletePerson: DeletePerson,
    val getRoomAndPerson: GetRoomAndPerson,
    val upsertSettings: UpsertSettings,
    val getSettingsById: GetSettingsById
)
