package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList

/** Manages the storage and retrieval of ToDoLists */
interface ToDoListRepository {

    /**
     * Get all ToDoLists
     * Note: no pagination
     * TODO add either limitations or pagination
     */
    suspend fun index(): Collection<ToDoList>

    /**
     * Get a specific ToDoList for a provided Id
     */
    suspend fun getById(id: Id): ToDoList?

    /**
     * Store a ToDoList
     */
    suspend fun store(toDoList: ToDoList)

    /**
     * Delete a ToDoList from storage
     */
    suspend fun delete(toDoList: ToDoList)
}