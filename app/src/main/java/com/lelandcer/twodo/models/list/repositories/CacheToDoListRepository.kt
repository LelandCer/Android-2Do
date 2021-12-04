package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.list.ToDoList

interface CacheToDoListRepository: ToDoListRepository {

    fun storeAll(toDoLists: Collection<ToDoList>)

}