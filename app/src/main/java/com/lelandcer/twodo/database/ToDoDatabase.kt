package com.lelandcer.twodo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lelandcer.twodo.database.dao.ToDoListDao
import com.lelandcer.twodo.database.dao.ToDoTaskDao
import com.lelandcer.twodo.database.models.ToDoList
import com.lelandcer.twodo.database.models.ToDoTask

@Database(entities = [ToDoList::class, ToDoTask::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoListDao(): ToDoListDao
    abstract fun toDoTaskDao(): ToDoTaskDao
}