package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.main.TwoDoApplication
import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

class RoomLocalStorageRepository @Inject constructor(idFactory: IdFactory): LocalStorageToDoListRepository {

    override suspend fun index(): MutableCollection<ToDoList> {
        val dbList = TwoDoApplication.db.toDoListDao().getAll()
        val list = ArrayList<ToDoList>()


        dbList.forEach {
            list.add(it.toToDoList())
        }
        return list
    }

    override suspend fun getById(id: Id): ToDoList? {
        return TwoDoApplication.db.toDoListDao().loadById(id.toString())?.toToDoList()
    }

    override suspend fun store(toDoList: ToDoList) {
        return TwoDoApplication.db.toDoListDao().insert(com.lelandcer.twodo.database.models.ToDoList.fromToDoList(toDoList))
    }

    override suspend fun delete(toDoList: ToDoList) {
        return TwoDoApplication.db.toDoListDao().delete(com.lelandcer.twodo.database.models.ToDoList.fromToDoList(toDoList))
    }
}