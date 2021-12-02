package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.list.ToDoList

/** Manages the storage and retrieval of Tasks
 * TODO evaluate the need for this vs managed completely by the list repository */
interface ToDoTaskRepository {

    /** Get all stored Tasks for a list
     * Note: no pagination for now
     */
    suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask>

    /**
     * Store a Task for a list
     */
    suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask)


    /**
     * Delete a Task from storage
     */
    suspend fun delete(toDoTask: ToDoTask)

}