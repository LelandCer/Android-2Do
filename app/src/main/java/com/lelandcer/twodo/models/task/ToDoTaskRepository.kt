package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.list.ToDoList

/** Manages the storage and retrieval of Tasks
 * TODO evaluate the need for this vs managed completely by the list repository */
interface ToDoTaskRepository {

    /** Get all stored Tasks for a list
     * Note: no pagination for now
     */
    fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask>

    /**
     * Store a Task for a list
     */
    fun store(toDoList: ToDoList, toDoTask: ToDoTask)

    /**
     * Create a new Task instance
     */
    fun create(toDoList: ToDoList, name: String): ToDoTask

    /**
     * Delete a Task from storage
     */
    fun delete(toDoTask: ToDoTask)

}