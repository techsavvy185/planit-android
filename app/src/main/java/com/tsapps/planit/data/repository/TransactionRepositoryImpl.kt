package com.tsapps.planit.data.repository

import com.tsapps.planit.data.database.TransactionDao
import com.tsapps.planit.data.spends.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) : TransactionRepository {
    override fun getTransactionsByDate(date: LocalDate?): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDate(date)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    override fun getTransactionsForDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForDateRange(startDate, endDate)
    }

    override fun getTransactionCountForDate(date: LocalDate): Flow<Int> {
        return transactionDao.getTransactionCountForDate(date)
    }
}