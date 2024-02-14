package com.example.rentproject.data.data_source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var roomDataBase : RoomDataBase
    private lateinit var roomDao: RoomDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup(){
        roomDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDataBase::class.java
        ).allowMainThreadQueries().build()
        roomDao = roomDataBase.roomDao
    }

    @After
    fun teardown(){
        roomDataBase.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insertRooms() = testScope.runBlockingTest {
        val rooms = mutableListOf( com.example.rentproject.domain.model.Room(
            roomId = 0,
            available = true,
            rent = 0f,
            roomName = "D1",
            reservationPeriod = 0,
            totalIncome = 0f,
            floorId = 0
        )
        )
        roomDao.insertRooms(rooms)
        val allRooms = roomDao.getRooms().collect()
        assertThat(allRooms).isEqualTo(rooms)
    }

    @Test
    fun deleteRoom() = testScope.runBlockingTest {
        val rooms = mutableListOf( com.example.rentproject.domain.model.Room(
            roomId = 0,
            available = true,
            rent = 0f,
            roomName = "D1",
            reservationPeriod = 0,
            totalIncome = 0f,
            floorId = 0
        )
        )
        roomDao.insertRooms(rooms)
        roomDao.deleteRoom(rooms[0])
        val allRooms = mutableListOf<com.example.rentproject.domain.model.Room>()
        roomDao.getRooms().collect{
           allRooms += it
            assertThat(allRooms).doesNotContain(rooms[0])
        }

    }
}