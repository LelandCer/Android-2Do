package com.lelandcer.twodo.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lelandcer.twodo.database.models.ToDoTask

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM ToDoTask WHERE listId = :listId")
    fun getAllForList(listId: String): List<ToDoTask>

    @Insert
    fun insert(toDoTask: ToDoTask)

    @Delete
    fun delete(toDoTask: ToDoTask)

    @Query("DELETE  FROM ToDoTask WHERE listId = :listId")
    fun deleteForList(listId: String)
}