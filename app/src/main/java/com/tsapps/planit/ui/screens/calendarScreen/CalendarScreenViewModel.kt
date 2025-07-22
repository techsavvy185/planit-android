package com.tsapps.planit.ui.screens.calendarScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsapps.planit.data.events.Event
import com.tsapps.planit.data.repository.EventRepository
import com.tsapps.planit.data.repository.TodoRepository
import com.tsapps.planit.data.repository.TransactionRepository
import com.tsapps.planit.data.spends.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val eventRepository: EventRepository,
    private val todoRepository: TodoRepository
): ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class)
    val events = _selectedDate.flatMapLatest {
        date -> eventRepository.getEventsByDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    val totalTodos = _selectedDate.flatMapLatest {
            date -> todoRepository.getTotalTodos(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    val totalCompletedTodos = _selectedDate.flatMapLatest {
            date -> todoRepository.getTotalCompletedTodos(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    val transactions: StateFlow<List<Transaction>> = _selectedDate.flatMapLatest{ date->
        transactionRepository.getTransactionsByDate(date)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onDateSelected(newDate: LocalDate) {
        _selectedDate.value = newDate
    }

    fun onPressDelete(transaction: Transaction) {
        viewModelScope.launch {
            transactionRepository.deleteTransaction(transaction)
        }
    }

    fun onPressDeleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }
}