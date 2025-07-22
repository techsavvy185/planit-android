package com.tsapps.planit.data.repository

import com.tsapps.planit.data.events.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventRepository {
    suspend fun insertEvent(event: Event)
    suspend fun deleteEvent(event: Event)
    fun getEventsByDate(date: LocalDate): Flow<List<Event>>
    fun getEventCountForDate(date: LocalDate): Flow<Int>
}