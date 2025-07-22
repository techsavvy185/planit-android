package com.tsapps.planit.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tsapps.planit.data.todo.TodoItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(todo: TodoItem)

    @Delete
    suspend fun deleteTodo(todo: TodoItem)

    @Query("SELECT * FROM todos WHERE date = :date ORDER BY id DESC")
    fun getTodosByDate(date: LocalDate?): Flow<List<TodoItem>>

    @Update
    suspend fun updateTodo(todo: TodoItem)
    @Query("SELECT COUNT(*) FROM todos WHERE date = :date")
    fun getTotalTodos(date: LocalDate?): Flow<Int>
    @Query("SELECT COUNT(*) FROM todos WHERE date = :date AND isCompleted = 1")
    fun getTotalCompletedTodos(date: LocalDate?): Flow<Int>

}