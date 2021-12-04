package com.lelandcer.twodo.database.room.repositories

import com.lelandcer.twodo.main.TwoDoApplication
import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.LocalStorageToDoListRepository
import javax.inject.Inject

class RoomLocalStorageToDoListRepository @Inject constructor() :
    LocalStorageToDoListRepository {
    private val toDoListDao = TwoDoApplication.db.toDoListDao()

    override suspend fun index(): MutableCollection<ToDoList> {
        val dbList = toDoListDao.getAll()
        val list = ArrayList<ToDoList>()


        dbList.forEach {
            list.add(it.toToDoList())
        }
        return list
    }

    override suspend fun getById(id: Id): ToDoList? {
        return toDoListDao.loadById(id)?.toToDoList()
    }

    override suspend fun store(toDoList: ToDoList) {
        return toDoListDao
            .insert(com.lelandcer.twodo.database.room.models.ToDoList.fromToDoList(toDoList))
    }

    override suspend fun delete(toDoList: ToDoList) {
        return toDoListDao
            .delete(com.lelandcer.twodo.database.room.models.ToDoList.fromToDoList(toDoList))
    }
}