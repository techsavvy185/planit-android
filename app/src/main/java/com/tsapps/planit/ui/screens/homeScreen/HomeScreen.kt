package com.tsapps.planit.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onNavigateToCalendar: () -> Unit
) {
    val quoteState by viewModel.quoteState.collectAsState()
    val transactionCount by viewModel.transactionCount.collectAsState()
    val eventCount by viewModel.eventCount.collectAsState()
    val todoSummary by viewModel.todoSummary.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {//Greeting
                Text(
                    text = viewModel.getGreeting(),
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "Here's a look at your day.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    if (quoteState is QuoteUiState.Success) {
                        val quote = (quoteState as QuoteUiState.Success).quote.quote
                        val author = (quoteState as QuoteUiState.Success).quote.author
                        Text(
                            text = "$quote\n\n- $author",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else if (quoteState is QuoteUiState.Loading) {
                        Text(
                            text = "Loading...",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        Text(
                            text = "Error fetching quote.",
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                }
                //At a glance
                Text(
                    text = "Today at a Glance",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SummaryCard(
                        title = "Events",
                        summaryText = "$eventCount scheduled",
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        title = "To-Dos",
                        summaryText = "${todoSummary.completed} / ${todoSummary.total} done",
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SummaryCard(
                    title = "Transactions",
                    summaryText = "$transactionCount made today",
                    modifier = Modifier.fillMaxWidth()
                )
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = onNavigateToCalendar)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Start planning your day!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, summaryText: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = summaryText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
