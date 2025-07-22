package com.tsapps.planit.data.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "todos")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val isCompleted: Boolean = false,
    val date: LocalDate
)
