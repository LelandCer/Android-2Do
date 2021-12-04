package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask

interface CacheToDoTaskRepository: ToDoTaskRepository {

    fun storeAll(toDoList: ToDoList,  toDoTasks: Collection<ToDoTask>)
}