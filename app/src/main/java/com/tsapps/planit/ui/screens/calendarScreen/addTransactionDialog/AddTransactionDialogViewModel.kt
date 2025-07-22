package com.tsapps.planit.ui.screens.calendarScreen.addTransactionDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsapps.planit.data.repository.TransactionRepository
import com.tsapps.planit.data.spends.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddTransactionDialogViewModel @Inject constructor(
    val transactionRepository: TransactionRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(AddTransactionDialogUIState())
    val uiState: StateFlow<AddTransactionDialogUIState> = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun onAmountChange(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
    }

    fun onCategoryChange(category: String) {
        _uiState.value = _uiState.value.copy(category = category)
    }


    fun onClickAdd(selectedDate: LocalDate) {
        viewModelScope.launch {
            val transaction = Transaction(
                title = uiState.value.title,
                amount = uiState.value.amount.toDoubleOrNull() ?: 0.0,
                category = uiState.value.category,
                date = selectedDate
            )
            transactionRepository.insertTransaction(transaction)
            _uiState.value = _uiState.value.copy(
                title = "",
                amount = "",
                category = ""
            )
        }
    }

}