package com.tsapps.planit.ui.screens.calendarScreen.addTransactionDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.LocalDate

@Composable
fun AddTransactionsDialog(
    onDismissRequest: () -> Unit,
    viewModel: AddTransactionDialogViewModel = hiltViewModel(),
    selectedDate: LocalDate
) {
    val uiState by viewModel.uiState.collectAsState()
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
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Add Transactions",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { newValue-> viewModel.onTitleChange(newValue) },
                    label = { Text(text = "Title") }
                )
                OutlinedTextField(
                    value = uiState.amount,
                    onValueChange = { newValue-> viewModel.onAmountChange(newValue) },
                    label = { Text(text = "Amount") }
                )
                OutlinedTextField(
                    value = uiState.category,
                    onValueChange = { newValue-> viewModel.onCategoryChange(newValue) },
                    label = { Text(text = "Category") }
                )

                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { viewModel.onClickAdd(selectedDate)
                            onDismissRequest()
                        }
                    ) {
                        Text(text = "Add")
                    }
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Close")
                    }
                }
            }
        }

    }
}