package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.list.List

/** Manages the storage and retrieval of Tasks
 * TODO evaluate the need for this vs managed completely by the list repository */
interface TaskRepository {

    /** Get all stored Tasks for a list
     * Note: no pagination for now
     */
    fun indexFor(list: List): MutableCollection<Task>

    /**
     * Store a Task for a list
     */
    fun store(list: List, task: Task)

    /**
     * Create a new Task instance
     */
    fun create(name: String): Task

    /**
     * Delete a Task from storage
     */
    fun delete(task: Task)

}