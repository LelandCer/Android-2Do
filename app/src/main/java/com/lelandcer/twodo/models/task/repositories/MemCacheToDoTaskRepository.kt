package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import javax.inject.Inject

/** A repository for saving the ToDoTasks in memory */
class MemCacheToDoTaskRepository @Inject constructor() : CacheToDoTaskRepository {
    override fun storeAll(toDoList: ToDoList, toDoTasks: Collection<ToDoTask>) {
        val newToDoTaskMapForList = HashMap<String, ToDoTask>()
        toDoTasks.forEach {
            newToDoTaskMapForList[it.id.getKey()] = it
        }
        taskMap[toDoList.id.getKey()] = newToDoTaskMapForList
    }

    override suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        val id = toDoList.id.getKey()
        val list = tasksForListId(id)
        return ArrayList(list.values)
    }

    override suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        val tasks = tasksForListId(toDoList.id.getKey())
        if (tasks.isEmpty()) return
        tasks[toDoTask.id.getKey()] = toDoTask

    }

    override suspend fun delete(toDoTask: ToDoTask) {
        val taskList = taskMap[toDoTask.listId.getKey()]
        taskList?.remove(toDoTask.id.getKey())
    }

    override suspend fun deleteAll(toDoList: ToDoList) {
        taskMap.remove(toDoList.id.getKey())
    }

    private fun tasksForListId(listId: String): MutableMap<String, ToDoTask> {
        return taskMap[listId] ?: HashMap()
    }

    companion object {
        // <listId, <taskId, task>>
        private val taskMap: MutableMap<String, MutableMap<String, ToDoTask>> = HashMap()
    }
}