package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

/** A repository that automatically manages both an cache and a local storage repositories */
class MultiToDoListRepository @Inject constructor(
    private val cacheToDoListRepository: CacheToDoListRepository,
    private val localStorageToDoListRepository: LocalStorageToDoListRepository
) : ToDoListRepository {


    override suspend fun index(): MutableCollection<ToDoList> {
        return cacheToDoListRepository.index()
    }

    override suspend fun getById(id: Id): ToDoList? {
        return cacheToDoListRepository.getById(id)
    }

    override suspend fun store(toDoList: ToDoList) {
        return cacheToDoListRepository.store(toDoList)
    }

    override suspend fun delete(toDoList: ToDoList) {
        return cacheToDoListRepository.delete(toDoList)
    }
}