package com.tsapps.planit.di

import android.content.Context
import com.tsapps.planit.data.database.EventDao
import com.tsapps.planit.data.database.PlanItDatabase
import com.tsapps.planit.data.database.TodoDao
import com.tsapps.planit.data.database.TransactionDao
import com.tsapps.planit.data.repository.EventRepository
import com.tsapps.planit.data.repository.EventRepositoryImpl
import com.tsapps.planit.data.repository.TodoRepository
import com.tsapps.planit.data.repository.TodoRepositoryImpl
import com.tsapps.planit.data.repository.TransactionRepository
import com.tsapps.planit.data.repository.TransactionRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlanItDatabase {
        return PlanItDatabase.getDatabase(context)
    }

    @Provides
    fun provideTransactionDao(database: PlanItDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }

    @Provides
    fun provideTodoDao(database: PlanItDatabase): TodoDao {
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }

    @Provides
    fun provideEventDao(database: PlanItDatabase): EventDao {
        return database.eventDao()
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventDao: EventDao): EventRepository {
        return EventRepositoryImpl(eventDao)
    }


}