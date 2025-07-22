package com.tsapps.planit.ui.screens.calendarScreen.addEventDialog

import java.sql.Time
import java.time.LocalDate

data class AddEventDialogUiState(
    val title: String = "",
    val date: LocalDate? = null,
    val startTime: String? = null,
    val isOnline: Boolean = false,
    val location: String = ""
)
