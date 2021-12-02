package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import javax.inject.Inject

class MultiToDoTaskRepository @Inject constructor(
    private val cacheToDoTaskRepository: CacheToDoTaskRepository,
    private val localStorageToDoTaskRepository: LocalStorageToDoTaskRepository
) : ToDoTaskRepository {
    override suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        return cacheToDoTaskRepository.indexFor(toDoList)
    }

    override suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        return cacheToDoTaskRepository.store(toDoList, toDoTask)
    }

    override suspend fun delete(toDoTask: ToDoTask) {
        return cacheToDoTaskRepository.delete(toDoTask)
    }
}