package com.lelandcer.twodo.models.task.repositories

import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import javax.inject.Inject

class RoomLocalStorageToDoTaskRepository @Inject constructor(private val idFactory: IdFactory): LocalStorageToDoTaskRepository {
    override suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        TODO("Not yet implemented")
    }

    override suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(toDoTask: ToDoTask) {
        TODO("Not yet implemented")
    }
}