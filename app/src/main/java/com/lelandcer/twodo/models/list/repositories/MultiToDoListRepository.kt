package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

/** A repository that automatically manages both an cache and a local storage repositories */
class MultiToDoListRepository @Inject constructor(
    private val cacheToDoListRepository: CacheToDoListRepository,
    private val localStorageToDoListRepository: LocalStorageToDoListRepository
) : ToDoListRepository {

    override suspend fun index(): Collection<ToDoList> {
        if (isCacheFresh) {
            return cacheToDoListRepository.index()
        }
        val toDoLists = localStorageToDoListRepository.index()
        cacheToDoListRepository.storeAll(toDoLists)
        isCacheFresh = true
        return toDoLists
    }

    override suspend fun getById(id: Id): ToDoList? {
        val list: ToDoList?
        if (isCacheFresh) {
            list = cacheToDoListRepository.getById(id)
            if (list != null) return list
        }
        return localStorageToDoListRepository.getById(id)
    }

    override suspend fun store(toDoList: ToDoList) {
        localStorageToDoListRepository.store(toDoList)
        if (isCacheFresh) cacheToDoListRepository.store(toDoList)
    }

    override suspend fun delete(toDoList: ToDoList) {
        localStorageToDoListRepository.delete(toDoList)
        cacheToDoListRepository.delete(toDoList)
    }

    companion object {
        /** Flag for if the cache has been seeded with the initial dataset or not */
        private var isCacheFresh = false
    }
}