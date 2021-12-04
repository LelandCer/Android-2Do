package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import javax.inject.Inject

class MultiToDoTaskRepository @Inject constructor(
    private val cacheToDoTaskRepository: CacheToDoTaskRepository,
    private val localStorageToDoTaskRepository: LocalStorageToDoTaskRepository
) : ToDoTaskRepository {
    override suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        var toDoTasks = cacheToDoTaskRepository.indexFor(toDoList)
        if (toDoTasks.isEmpty()) {
            toDoTasks = localStorageToDoTaskRepository.indexFor(toDoList)
            cacheToDoTaskRepository.storeAll(toDoList, toDoTasks)
        }
        return toDoTasks
    }

    override suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        localStorageToDoTaskRepository.store(toDoList, toDoTask)
        cacheToDoTaskRepository.store(toDoList, toDoTask)
    }

    override suspend fun delete(toDoTask: ToDoTask) {
        localStorageToDoTaskRepository.delete(toDoTask)
        cacheToDoTaskRepository.delete(toDoTask)
    }

    override suspend fun deleteAll(toDoList: ToDoList) {
        localStorageToDoTaskRepository.deleteAll(toDoList)
        cacheToDoTaskRepository.deleteAll(toDoList)
    }
}