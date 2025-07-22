package com.tsapps.planit.ui.screens.calendarScreen.addEventDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsapps.planit.data.events.Event
import com.tsapps.planit.data.repository.EventRepository
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
class AddEventDialogViewModel @Inject constructor(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AddEventDialogUiState())
    val uiState: StateFlow<AddEventDialogUiState> = _uiState.asStateFlow()

    fun getEventsByDate(selectedDate: LocalDate): StateFlow<List<Event>> {
        return eventRepository.getEventsByDate(selectedDate).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }


    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }
    fun onStartTimeChange(startTime: String) {
        _uiState.value = _uiState.value.copy(startTime = startTime)
    }

    fun onIsOnlineChange(isOnline: Boolean) {
        _uiState.value = _uiState.value.copy(isOnline = isOnline)
    }
    fun onLocationChange(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
    }

    fun onClickAdd(event: Event){
        viewModelScope.launch {
            eventRepository.insertEvent(event)
        }
        _uiState.value = _uiState.value.copy(title = "", startTime = "", isOnline = false, location = "")
    }

    fun onClickDelete(event: Event) {
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
        }
    }
}