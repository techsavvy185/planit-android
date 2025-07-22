package com.tsapps.planit.data.repository

import com.tsapps.planit.data.spends.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionRepository {
    fun getTransactionsByDate(date: LocalDate?): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    fun getTransactionsForDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>
    fun getTransactionCountForDate(date: LocalDate): Flow<Int>
}