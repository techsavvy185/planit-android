package com.tsapps.planit.ui.screens.calendarScreen.todoDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate

@Composable
fun TodoDialog(
    viewModel: TodoDialogViewModel = hiltViewModel(),
    selectedDate: LocalDate,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        TodoDialogLayout(
            viewModel = viewModel,
            selectedDate = selectedDate,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
fun TodoDialogLayout(
    viewModel: TodoDialogViewModel,
    selectedDate: LocalDate,
    onDismissRequest: () -> Unit
) {
    val newTaskText by viewModel.newTaskText.collectAsState()
    val todosStateFlow = remember(selectedDate) {
        viewModel.getTodos(selectedDate)
    }
    val todoList by todosStateFlow.collectAsState()


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "To-Do items",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${selectedDate.dayOfMonth} ${selectedDate.dayOfWeek}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                items(items = todoList, key = { it.id }) { item ->
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = item.isCompleted,
                            onCheckedChange = {
                                viewModel.onTodoCheckedChange(item)
                            }
                        )

                        val textStyle = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (item.isCompleted) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            },
                            color = if (item.isCompleted) {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )

                        BasicTextField(
                            value = item.task,
                            modifier = Modifier.weight(1f),
                            onValueChange = { newText ->
                                viewModel.onTodoTaskChange(item, newText)
                            },
                            textStyle = textStyle
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTaskText,
                    onValueChange = {
                        viewModel.onNewTaskTextChange(it)
                    },
                    label = {
                        Text(text = "New task")
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.onAddTask(selectedDate)
                              },
                    enabled = newTaskText.isNotBlank()
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onDismissRequest) {
                Text(text = "Close")
            }
        }
    }
}