package com.tsapps.planit.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tsapps.planit.data.events.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events WHERE date = :date ORDER BY startTime ASC")
    fun getEventsByDate(date: LocalDate): Flow<List<Event>>
    @Query("SELECT COUNT(*) FROM events WHERE date = :date")
    fun getEventCountForDate(date: LocalDate): Flow<Int>
}