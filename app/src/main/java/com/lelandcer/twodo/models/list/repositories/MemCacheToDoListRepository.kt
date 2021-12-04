package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

/** A repository for saving lists in memory */
class MemCacheToDoListRepository @Inject constructor() : CacheToDoListRepository {
    override fun storeAll(toDoListsS: Collection<ToDoList>) {
        toDoLists.clear()
        toDoLists.addAll(toDoListsS)
    }

    override suspend fun index(): MutableList<ToDoList> {
        return toDoLists
    }

    override suspend fun getById(id: Id): ToDoList? {
        return toDoLists.firstOrNull { it.id == id }
    }

    override suspend fun store(toDoList: ToDoList) {
        val pos = toDoLists.indexOf(toDoList)
        if (pos < 0) {
            toDoLists.add(toDoList)
        } else {
            toDoLists[pos] = toDoList
        }
    }

    override suspend fun delete(toDoList: ToDoList) {
        toDoLists.remove(toDoList)
    }


    companion object {
        private val toDoLists: MutableList<ToDoList> = ArrayList()
    }
}