package com.lelandcer.twodo.database.dao

import androidx.room.*
import com.lelandcer.twodo.database.models.ToDoList

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM ToDoList")
    fun getAll(): List<ToDoList>

    @Query("SELECT * FROM todolist WHERE id = :listId")
    fun loadById(listId: String): ToDoList?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoList: ToDoList)

    @Delete
    fun delete(toDoList: ToDoList)
}