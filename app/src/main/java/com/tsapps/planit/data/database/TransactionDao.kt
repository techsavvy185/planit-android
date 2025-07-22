package com.tsapps.planit.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tsapps.planit.data.spends.Transaction
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT COUNT(*) FROM transactions WHERE date = :date")
    fun getTransactionCountForDate(date: LocalDate): Flow<Int>
    @Query("SELECT * FROM transactions WHERE date = :date ORDER BY id DESC")
    fun getTransactionsByDate(date: LocalDate?): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate")
    fun getTransactionsForDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Transaction>>

}