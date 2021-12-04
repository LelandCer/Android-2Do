package com.lelandcer.twodo.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDoList::class, ToDoTask::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
    abstract fun toDoTaskDao(): ToDoTaskDao
}