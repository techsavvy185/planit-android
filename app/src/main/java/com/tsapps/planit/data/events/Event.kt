package com.tsapps.planit.data.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.LocalDate
import java.util.Date


@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: LocalDate,
    val startTime: String?,
    val isOnline: Boolean = false,
    val location: String
)
