package com.tsapps.planit.ui.screens.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsapps.planit.data.network.Quote
import com.tsapps.planit.data.network.QuoteApiService
import com.tsapps.planit.data.repository.EventRepository
import com.tsapps.planit.data.repository.TodoRepository
import com.tsapps.planit.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

data class TodoSummary(
    val total: Int = 0,
    val completed: Int = 0
)
class QuoteRepository @Inject constructor(
    private val apiService: QuoteApiService
) {
    suspend fun getRandomQuote(): Quote? {
        return apiService.getRandomQuote().firstOrNull()
    }
}

sealed interface QuoteUiState {
    data object Loading: QuoteUiState
    data class Error(val message: String): QuoteUiState
    data class Success(val quote: Quote): QuoteUiState
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    private val transactionRepository: TransactionRepository,
    private val eventRepository: EventRepository,
    private val todoRepository: TodoRepository
): ViewModel() {
    fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    private val _quoteState = MutableStateFlow<QuoteUiState>(QuoteUiState.Loading)
    val quoteState = _quoteState.asStateFlow()

    init {
        fetchQuote()
    }

    fun fetchQuote() {
        viewModelScope.launch {
            _quoteState.value = QuoteUiState.Loading
            try {
                quoteRepository.getRandomQuote()?.let { quote ->
                    _quoteState.value = QuoteUiState.Success(quote)
                } ?: run {
                    _quoteState.value = QuoteUiState.Error("No quote found")
                }
                } catch (e: Exception) {
                _quoteState.value = QuoteUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    private val todayLocalDate: LocalDate = LocalDate.now()

    val transactionCount: StateFlow<Int> = transactionRepository
        .getTransactionCountForDate(todayLocalDate)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val eventCount: StateFlow<Int> = eventRepository
        .getEventCountForDate(todayLocalDate)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val todoSummary: StateFlow<TodoSummary> = combine(
        todoRepository.getTotalTodos(todayLocalDate),
        todoRepository.getTotalCompletedTodos(todayLocalDate)
    ) { total, completed ->
        TodoSummary(total = total, completed = completed)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodoSummary())
}