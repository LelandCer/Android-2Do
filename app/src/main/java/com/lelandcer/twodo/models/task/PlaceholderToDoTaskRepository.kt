package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDIdFactory
import com.lelandcer.twodo.models.list.ToDoList

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderToDoTaskRepository : ToDoTaskRepository {
    private val idFactory: IdFactory = UUIDIdFactory()
    override fun indexFor(toDoList: ToDoList): MutableCollection<ToDoTask> {
        return toDoList.toDoTasks
    }

    override fun store(toDoList: ToDoList, toDoTask: ToDoTask) {
        toDoList.toDoTasks.add(toDoTask)
    }

    override fun create(toDoList: ToDoList, name: String): ToDoTask {
        return ToDoTask(idFactory.makeId(), toDoList.id, name)
    }

    override fun delete(toDoTask: ToDoTask) {
        TODO("Not yet implemented")
    }
}