package com.lelandcer.twodo.models.task

import android.util.Log
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDIdFactory
import com.lelandcer.twodo.models.list.ToDoList
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderToDoTaskRepository @Inject constructor() : ToDoTaskRepository {
    private val idFactory: IdFactory = UUIDIdFactory()
    override fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        val id = toDoList.id.getKey()
        val list = taskMap[id] ?: ArrayList()
        taskMap[id] = list
        return list
    }

    override fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        val tasks = indexFor(toDoList)
        tasks.add(toDoTask)
    }

    override fun create(toDoList: ToDoList, name: String): ToDoTask {
        return ToDoTask(idFactory.makeId(), toDoList.id, name)
    }

    override fun delete(toDoTask: ToDoTask) {
        val taskList = taskMap[toDoTask.listId.getKey()]
        taskList?.remove(toDoTask)
    }

    companion object {
        private val taskMap: MutableMap<String, MutableCollection<ToDoTask>> = HashMap()
    }
}