package com.tsapps.planit.data.repository

import com.tsapps.planit.data.todo.TodoItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TodoRepository {
    fun getTodosByDate(date: LocalDate?): Flow<List<TodoItem>>
    suspend fun insertTodo(todo: TodoItem)
    suspend fun deleteTodo(todo: TodoItem)
    suspend fun updateTodo(todo: TodoItem)
    fun getTotalTodos(date: LocalDate?): Flow<Int>
    fun getTotalCompletedTodos(date: LocalDate?): Flow<Int>

}