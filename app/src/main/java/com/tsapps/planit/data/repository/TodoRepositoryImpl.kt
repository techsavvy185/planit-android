package com.tsapps.planit.data.repository

import com.tsapps.planit.data.database.TodoDao
import com.tsapps.planit.data.todo.TodoItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TodoRepositoryImpl(private val todoDao: TodoDao) : TodoRepository  {
    override fun getTodosByDate(date: LocalDate?): Flow<List<TodoItem>> {
        return todoDao.getTodosByDate(date)
    }

    override suspend fun insertTodo(todo: TodoItem) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoItem) {
        todoDao.deleteTodo(todo)
    }

    override suspend fun updateTodo(todo: TodoItem) {
        todoDao.updateTodo(todo)
    }
    override fun getTotalTodos(date: LocalDate?): Flow<Int> {
        return todoDao.getTotalTodos(date)
    }
    override fun getTotalCompletedTodos(date: LocalDate?): Flow<Int> {
        return todoDao.getTotalCompletedTodos(date)
    }
}