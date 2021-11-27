package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import java.util.*

/** Manages the storage and retrieval of Lists */
interface ToDoListRepository {

    /**
     * Get all Lists
     * Note: no pagination
     * TODO add either limitations or pagination
     */
    fun index(): MutableCollection<ToDoList>

    /**
     * Get a specific List for a provided Id
     */
    fun getById(id: Id): ToDoList?

    /**
     * Create a new List
     */
    fun create(name: String, dueAt: Date): ToDoList

    /**
     * Store a List
     */
    fun store(toDoList: ToDoList)

    /**
     * Delete a List from storage
     */
    fun delete(toDoList: ToDoList)
}