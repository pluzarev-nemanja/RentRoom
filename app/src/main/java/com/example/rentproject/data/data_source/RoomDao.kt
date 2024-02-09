package com.example.rentproject.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.rentproject.data.data_source.relations.FloorWithRooms
import com.example.rentproject.data.data_source.relations.RoomAndPerson
import com.example.rentproject.domain.model.Floor
import com.example.rentproject.domain.model.Person
import com.example.rentproject.domain.model.Room
import com.example.rentproject.domain.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Query("SELECT * FROM room ORDER BY roomId ASC")
    fun getRooms(): Flow<List<Room>>

    @Query("SELECT * FROM room WHERE roomId = :id")
    suspend fun getRoomById(id: Int): Room?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRooms(roomsList: MutableList<Room>)

    @Update
    suspend fun upsertRoom(room: Room)

    @Delete
    suspend fun deleteRoom(room: Room)

    @Transaction
    @Query("SELECT * FROM floor WHERE floorId = :floorId")
    fun getFloorWithRooms(floorId : Int) : Flow<List<FloorWithRooms>>

    @Upsert
    suspend fun insertFloors(floors: List<Floor>)

    @Upsert
    suspend fun upsertPerson(person: Person)

    @Delete
    suspend fun deletePerson(person: Person)

    @Transaction
    @Query("SELECT * FROM room WHERE roomId = :roomId")
    fun getRoomAndPersonWithRoomId(roomId : Int): Flow<List<RoomAndPerson>>

    @Upsert
    suspend fun upsertSettings(settings: Settings)

    @Query("SELECT * FROM Settings WHERE id = :id")
    fun getSettings(id: Int): Flow<Settings>

}