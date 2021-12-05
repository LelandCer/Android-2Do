package com.lelandcer.twodo.database.room.dao

import androidx.room.*
import com.lelandcer.twodo.database.room.models.ToDoList
import com.lelandcer.twodo.models.id.Id

@Dao
interface ToDoListDao {
    /** Retrieve all ToDoLists from the db */
    @Query("SELECT * FROM ToDoList")
    fun getAll(): List<ToDoList>

    /** Retrieves a single ToDoList from the db by id */
    @Query("SELECT * FROM todolist WHERE id = :listId")
    fun loadById(listId: Id): ToDoList?

    /** Inserts a new ToDoList into the db, or replaces a ToDoList if the id already exists */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoList: ToDoList)

    /** Deletes a ToDoList */
    @Delete
    fun delete(toDoList: ToDoList)
}