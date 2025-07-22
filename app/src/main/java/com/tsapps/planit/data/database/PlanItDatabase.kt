package com.tsapps.planit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.tsapps.planit.data.events.Event
import com.tsapps.planit.data.spends.Transaction
import com.tsapps.planit.data.todo.TodoItem
import java.sql.Time
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}

class TimeConverter {
    @TypeConverter
    fun fromTimeInMillis(value: Long?): Time? {
        return value?.let { Time(it) }
    }

    @TypeConverter
    fun timeToTimeInMillis(time: Time?): Long? {
        return time?.time
    }
}

@Database(entities = [Transaction::class, TodoItem::class, Event::class], version = 3)
@TypeConverters(LocalDateConverter::class, TimeConverter::class)
abstract class PlanItDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun todoDao(): TodoDao
    abstract fun eventDao(): EventDao

    companion object{
        @Volatile
        private var INSTANCE: PlanItDatabase? = null

        fun getDatabase(context: Context): PlanItDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlanItDatabase::class.java,
                    "planit_database"
                )
                    .fallbackToDestructiveMigration() //delete when testing done
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}