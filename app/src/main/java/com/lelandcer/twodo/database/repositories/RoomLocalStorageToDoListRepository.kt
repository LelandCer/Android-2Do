package com.lelandcer.twodo.database.repositories

import com.lelandcer.twodo.main.TwoDoApplication
import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.LocalStorageToDoListRepository
import javax.inject.Inject
import com.lelandcer.twodo.database.models.ToDoList as DbToDoList

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
        return toDoListDao.loadById(id.toString())?.toToDoList()
    }

    override suspend fun store(toDoList: ToDoList) {
        return toDoListDao
            .insert(DbToDoList.fromToDoList(toDoList))
    }

    override suspend fun delete(toDoList: ToDoList) {
        return toDoListDao
            .delete(DbToDoList.fromToDoList(toDoList))
    }
}