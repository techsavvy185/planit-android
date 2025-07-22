package com.tsapps.planit.data.repository

import com.tsapps.planit.data.database.EventDao
import com.tsapps.planit.data.events.Event
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {
    override suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }

    override fun getEventsByDate(date: LocalDate): Flow<List<Event>> {
        return eventDao.getEventsByDate(date)
    }
    override fun getEventCountForDate(date: LocalDate): Flow<Int> {
        return eventDao.getEventCountForDate(date)
    }


}