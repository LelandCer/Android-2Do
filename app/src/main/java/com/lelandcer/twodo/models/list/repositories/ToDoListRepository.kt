package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList

/** Manages the storage and retrieval of Lists */
interface ToDoListRepository {

    /**
     * Get all Lists
     * Note: no pagination
     * TODO add either limitations or pagination
     */
    suspend fun index(): MutableCollection<ToDoList>

    /**
     * Get a specific List for a provided Id
     */
    suspend fun getById(id: Id): ToDoList?

    /**
     * Store a List
     */
    suspend fun store(toDoList: ToDoList)

    /**
     * Delete a List from storage
     */
    suspend fun delete(toDoList: ToDoList)
}