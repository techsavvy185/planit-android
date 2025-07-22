package com.tsapps.planit.ui.screens.calendarScreen.todoDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsapps.planit.data.repository.TodoRepository
import com.tsapps.planit.data.todo.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoDialogViewModel @Inject constructor(
    private val todoRepository: TodoRepository
): ViewModel() {
    fun getTodos(
        selectedDate: LocalDate
    ): StateFlow<List<TodoItem>> {
        return todoRepository.getTodosByDate(selectedDate).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    private val _newTaskText = MutableStateFlow("")
    val newTaskText: StateFlow<String> = _newTaskText.asStateFlow()

    fun onAddTask(selectedDate: LocalDate) {
        if (_newTaskText.value.isBlank()) {
            return
        }
        viewModelScope.launch {
            todoRepository.insertTodo(
                TodoItem(
                    task = newTaskText.value,
                    date = selectedDate,
                    isCompleted = false
                )
            )
            _newTaskText.value = ""
        }
    }

    fun onNewTaskTextChange(newText: String) {
        _newTaskText.value = newText
    }


    fun onTodoCheckedChange(todoItem: TodoItem) {
        viewModelScope.launch {
            todoRepository.updateTodo(todoItem.copy(isCompleted = !todoItem.isCompleted))
        }
    }

    fun onTodoTaskChange(todoItem: TodoItem, newText: String) {
        viewModelScope.launch {
            todoRepository.updateTodo(todoItem.copy(task = newText))
        }
    }


}