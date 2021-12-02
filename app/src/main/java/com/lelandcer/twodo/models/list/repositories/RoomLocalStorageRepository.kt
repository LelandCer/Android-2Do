package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

class RoomLocalStorageRepository @Inject constructor(idFactory: IdFactory): LocalStorageToDoListRepository {

    override suspend fun index(): MutableCollection<ToDoList> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Id): ToDoList? {
        TODO("Not yet implemented")
    }

    override suspend fun store(toDoList: ToDoList) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(toDoList: ToDoList) {
        TODO("Not yet implemented")
    }
}