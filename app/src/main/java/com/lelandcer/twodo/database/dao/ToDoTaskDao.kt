package com.lelandcer.twodo.database.dao

import androidx.room.*
import com.lelandcer.twodo.database.models.ToDoTask
import com.lelandcer.twodo.models.id.Id

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM ToDoTask WHERE listId = :listId")
    fun getAllForList(listId: Id): List<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoTask: ToDoTask)

    @Delete
    fun delete(toDoTask: ToDoTask)

    @Query("DELETE  FROM ToDoTask WHERE listId = :listId")
    fun deleteForList(listId: Id)
}