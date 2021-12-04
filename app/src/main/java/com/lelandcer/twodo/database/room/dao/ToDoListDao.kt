package com.lelandcer.twodo.database.room.dao

import androidx.room.*
import com.lelandcer.twodo.database.room.models.ToDoList
import com.lelandcer.twodo.models.id.Id

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM ToDoList")
    fun getAll(): List<ToDoList>

    @Query("SELECT * FROM todolist WHERE id = :listId")
    fun loadById(listId: Id): ToDoList?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoList: ToDoList)

    @Delete
    fun delete(toDoList: ToDoList)
}