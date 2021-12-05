package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask

/**
 * An interface for ToDoTaskRepository.
 *  Identifies repository implementations that are non-persistent between app launches, but have high responsiveness
 */
interface CacheToDoTaskRepository : ToDoTaskRepository {

    fun storeAll(toDoList: ToDoList, toDoTasks: Collection<ToDoTask>)
}