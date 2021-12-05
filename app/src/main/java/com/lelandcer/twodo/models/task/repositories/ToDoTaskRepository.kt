package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask

/**
 * Manages the storage and retrieval of ToDoTasks
 */
interface ToDoTaskRepository {

    /** Get all stored ToDoTasks for a ToDoList
     * Note: no pagination for now
     */
    suspend fun indexFor(toDoList: ToDoList): Collection<ToDoTask>

    /**
     * Store a ToDoTask for a ToDoList
     */
    suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask)


    /**
     * Delete a ToDoTask from storage
     */
    suspend fun delete(toDoTask: ToDoTask)

    /**
     * Delete all ToDoTask from storage for a ToDoList
     */
    suspend fun deleteAll(toDoList: ToDoList)

}