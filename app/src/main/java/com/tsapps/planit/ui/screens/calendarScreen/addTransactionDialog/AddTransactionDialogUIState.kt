package com.tsapps.planit.ui.screens.calendarScreen.addTransactionDialog


data class AddTransactionDialogUIState(
    val isLoading: Boolean = false,
    val title: String = "",
    val amount: String = "",
    val category: String = ""
)
