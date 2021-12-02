package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import javax.inject.Inject

/** A repository for saving the tasks in memory */
class MemCacheToDoTaskRepository @Inject constructor() : CacheToDoTaskRepository {
    override suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        val id = toDoList.id.getKey()
        val list = tasksForListId(id)
        return ArrayList(list.values)
    }

    override suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        val tasks = tasksForListId(toDoList.id.getKey())
        tasks[toDoTask.id.getKey()] = toDoTask

    }

    override suspend fun delete(toDoTask: ToDoTask) {
        val taskList = taskMap[toDoTask.listId.getKey()]
        taskList?.remove(toDoTask.id.getKey())
    }

    private fun tasksForListId(listId: String): MutableMap<String, ToDoTask> {
        val list = taskMap[listId] ?: HashMap()
        taskMap[listId] = list
        return list
    }

    companion object {
        private val taskMap: MutableMap<String, MutableMap<String, ToDoTask>> = HashMap()
    }
}