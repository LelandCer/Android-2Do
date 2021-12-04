package com.lelandcer.twodo.database.room.dao

import androidx.room.*
import com.lelandcer.twodo.database.room.models.ToDoTask
import com.lelandcer.twodo.models.id.Id

@Dao
interface ToDoTaskDao {

    /** Retrieves all ToDoTasks for a ToDoList id */
    @Query("SELECT * FROM ToDoTask WHERE listId = :listId")
    fun getAllForList(listId: Id): List<ToDoTask>

    /** Inserts a new ToDoTask, or replaces one if the id already exists */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoTask: ToDoTask)

    /** Deletes a ToDoTask */
    @Delete
    fun delete(toDoTask: ToDoTask)

    /** Deletes all ToDoTasks for a ToDoList */
    @Query("DELETE  FROM ToDoTask WHERE listId = :listId")
    fun deleteForList(listId: Id)
}