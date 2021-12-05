package com.lelandcer.twodo.database.room.repositories


import com.lelandcer.twodo.main.TwoDoApplication
import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.repositories.LocalStorageToDoTaskRepository
import javax.inject.Inject

/** A Room DB implementation of the ToDoTaskRepository */
class RoomLocalStorageToDoTaskRepository @Inject constructor() : LocalStorageToDoTaskRepository {
    private val toDoTaskDao = TwoDoApplication.db.toDoTaskDao()
    override suspend fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        val dbTasks = toDoTaskDao.getAllForList(toDoList.id)
        val tasks = ArrayList<ToDoTask>()

        dbTasks.forEach {
            tasks.add(it.toToDoTask())
        }
        return tasks

    }

    override suspend fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        toDoTaskDao.insert(com.lelandcer.twodo.database.room.models.ToDoTask.fromToDoTask(toDoTask))
    }

    override suspend fun delete(toDoTask: ToDoTask) {
        toDoTaskDao.delete(com.lelandcer.twodo.database.room.models.ToDoTask.fromToDoTask(toDoTask))
    }

    override suspend fun deleteAll(toDoList: ToDoList) {
        toDoTaskDao.deleteForList(toDoList.id)
    }
}