package com.tsapps.planit.ui.screens.calendarScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.tsapps.planit.data.events.Event
import com.tsapps.planit.data.spends.Transaction
import com.tsapps.planit.ui.screens.calendarScreen.addEventDialog.AddEventDialog
import com.tsapps.planit.ui.screens.calendarScreen.addTransactionDialog.AddTransactionsDialog
import com.tsapps.planit.ui.screens.calendarScreen.todoDialog.TodoDialog
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    viewModel: CalendarScreenViewModel = hiltViewModel()
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val events by viewModel.events.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarScreenLayout(
            selectedDate = selectedDate,
            onDateSelected = { newDate -> viewModel.onDateSelected(newDate) },
            transactions = transactions,
            viewModel = viewModel,
            events = events
        )
    }
}

@Composable
fun CalendarScreenLayout(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    transactions: List<Transaction>,
    viewModel: CalendarScreenViewModel,
    events: List<Event>,
) {
    var showTransactionsDialog by remember { mutableStateOf(false) }
    var showAddTransactionDialog by remember { mutableStateOf(false) }
    var showTodoDialog by remember { mutableStateOf(false) }
    var showEventDialog by remember { mutableStateOf(false) }
    if (showTransactionsDialog) {
        TransactionsDialog(
            transactions = transactions,
            onDismissRequest = { showTransactionsDialog = false },
            selectedDate = selectedDate,
            viewModel = viewModel,
        )
    }
    if (showAddTransactionDialog) {
        AddTransactionsDialog(
            onDismissRequest = { showAddTransactionDialog = false },
            selectedDate = selectedDate
        )
    }
    if (showTodoDialog) {
        TodoDialog(
            onDismissRequest = { showTodoDialog = false },
            selectedDate = selectedDate
        )
    }
    if(showEventDialog) {
        AddEventDialog(
            onDismissRequest = { showEventDialog = false },
            selectedDate = selectedDate
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            CalendarTopBar(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
            Row {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(300.dp)
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .clickable(
                            onClick = {
                                showTransactionsDialog = true
                            }
                        )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Day's total:",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${transactions.sumOf { it.amount }}",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(8.dp)
                        )
                        TextButton(onClick = { showAddTransactionDialog = true }) {
                            Text(text = "Add Transaction")
                        }
                    }

                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                        .clickable(
                            onClick = {
                                showTodoDialog = true
                            }
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.6f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "To-Do List",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = if (viewModel.totalTodos.collectAsState(0).value>0) "Tasks Completed: ${viewModel.totalCompletedTodos.collectAsState(0).value}/${viewModel.totalTodos.collectAsState(0).value}" else "No items added for the day.",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(8.dp)

                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp)
                    .clickable(onClick = { showEventDialog = true })
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                    Text(
                        text = "Add Event",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = events, key = { it.id }) { event ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp)) {
                        Row(Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(5f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = event.startTime ?: "",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (!event.isOnline) event.location else "Online",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                            IconButton(onClick = { viewModel.onPressDeleteEvent(event) },
                                modifier = Modifier.weight(1f)) {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
                            }

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DayItem(
    date: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    isSelected: Boolean,
) {
    val dateText = date.dayOfMonth.toString()
    val dayText = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    Card(
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = { onDateSelected(date) })
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()

                .background(if (isSelected) Color.White else Color.Transparent)

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = dateText,
                    color = if (isSelected) Color.Black else {
                        MaterialTheme.colorScheme.onSurface}
                )
                Text(text = dayText,
                    color = if (isSelected) Color.Black else {
                        MaterialTheme.colorScheme.onSurface}
                )
            }
        }
    }
}


@Composable
fun CalendarTopBar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {

    val today =  LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val weekDays = List(7) { dayIndex ->
        startOfWeek.plusDays(dayIndex.toLong())
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clip(RoundedCornerShape(4.dp))
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(horizontal = 6.dp)
        ) {
            items(items = weekDays, key = { it.toEpochDay() }) { date ->
                DayItem(
                    date = date,
                    isSelected = selectedDate == date,
                    onDateSelected = { onDateSelected(date) }
                )
            }
        }
    }
}


@Composable
fun TransactionsDialog(
    transactions: List<Transaction>,
    onDismissRequest: () -> Unit,
    selectedDate: LocalDate,
    viewModel: CalendarScreenViewModel
    ) {
    Dialog(
        onDismissRequest = onDismissRequest

    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start

                ) {
                    Text(
                        text = "Transactions for: ",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "${selectedDate.dayOfWeek} ${selectedDate.dayOfMonth}",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.height(16.dp))

                if (transactions.isEmpty()) {
                    Text(text = "No transactions for this day.",
                        style = MaterialTheme.typography.bodyMedium,)
                } else {
                    LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                        items(items = transactions, key = { it.id }) { transaction->
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text(text = transaction.title,
                                        style = MaterialTheme.typography.bodyLarge)
                                    Text(text = transaction.category,
                                        style = MaterialTheme.typography.bodyMedium)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = "₹${transaction.amount}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                IconButton(
                                    onClick = { viewModel.onPressDelete(transaction) }
                                ) {
                                    Icon(imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete Button"
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Close")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}