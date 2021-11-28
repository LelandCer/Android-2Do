package com.lelandcer.twodo.models.task

import android.util.Log
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDIdFactory
import com.lelandcer.twodo.models.list.ToDoList
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderToDoTaskRepository @Inject constructor() : ToDoTaskRepository {
    private val idFactory: IdFactory = UUIDIdFactory()
    override fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        val id = toDoList.id.getKey()
        val list = tasksForListId(id)
        return list.values
    }

    override fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        val tasks = tasksForListId(toDoList.id.getKey())
        tasks.put(toDoTask.id.getKey(), toDoTask)

    }

    override fun create(toDoList: ToDoList, name: String): ToDoTask {
        return ToDoTask(idFactory.makeId(), toDoList.id, name)
    }

    override fun delete(toDoTask: ToDoTask) {
        val taskList = taskMap[toDoTask.listId.getKey()]
        taskList?.remove(toDoTask.id.getKey())
    }

    private fun tasksForListId(listId: String): MutableMap<String, ToDoTask> {
        val list  = taskMap[listId] ?: HashMap()
        taskMap[listId] = list
        return list
    }

    companion object {
        private val taskMap: MutableMap<String, MutableMap<String,ToDoTask>> = HashMap()
    }
}