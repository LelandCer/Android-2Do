package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import javax.inject.Inject

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderToDoListRepository @Inject constructor() : ToDoListRepository {

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