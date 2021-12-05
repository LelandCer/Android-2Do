package com.lelandcer.twodo.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lelandcer.twodo.database.room.dao.ToDoListDao
import com.lelandcer.twodo.database.room.dao.ToDoTaskDao
import com.lelandcer.twodo.database.room.models.ToDoList
import com.lelandcer.twodo.database.room.models.ToDoTask

/** Registers the different database configuration settings */
@Database(entities = [ToDoList::class, ToDoTask::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
    abstract fun toDoTaskDao(): ToDoTaskDao
}