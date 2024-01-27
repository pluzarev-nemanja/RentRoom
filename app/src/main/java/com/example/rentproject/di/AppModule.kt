package com.example.rentproject.di

import android.app.Application
import androidx.room.Room
import com.example.rentproject.data.data_source.RoomDataBase
import com.example.rentproject.data.repository.RoomRepositoryImpl
import com.example.rentproject.domain.repository.RoomRepository
import com.example.rentproject.domain.use_case.DeletePerson
import com.example.rentproject.domain.use_case.DeleteRoom
import com.example.rentproject.domain.use_case.GetFloorWithRooms
import com.example.rentproject.domain.use_case.GetRoomById
import com.example.rentproject.domain.use_case.GetRooms
import com.example.rentproject.domain.use_case.InsertFloors
import com.example.rentproject.domain.use_case.InsertRooms
import com.example.rentproject.domain.use_case.RoomUseCases
import com.example.rentproject.domain.use_case.UpsertPerson
import com.example.rentproject.domain.use_case.UpsertRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomsDatabase(app: Application): RoomDataBase {
        return Room.databaseBuilder(
            app,
            RoomDataBase::class.java,
            RoomDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRoomsRepository(roomDb: RoomDataBase): RoomRepository {
        return RoomRepositoryImpl(
            roomDb.roomDao
        )
    }

    @Provides
    @Singleton
    fun provideRoomUseCases(repository: RoomRepository):RoomUseCases{
        return RoomUseCases(
            deleteRoom = DeleteRoom(repository),
            getRoomById = GetRoomById(repository),
            getRooms = GetRooms(repository),
            upsertRoom = UpsertRoom(repository),
            insertRooms = InsertRooms(repository),
            getFloorWithRooms = GetFloorWithRooms(repository),
            insertFloors = InsertFloors(repository),
            upsertPerson = UpsertPerson(repository),
            deletePerson = DeletePerson(repository)
        )
    }

}