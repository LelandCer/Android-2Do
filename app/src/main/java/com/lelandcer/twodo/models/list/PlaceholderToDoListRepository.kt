package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDIdFactory
import com.lelandcer.twodo.models.task.PlaceholderToDoTaskRepository
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderToDoListRepository @Inject constructor() : ToDoListRepository {
    private val toDoTaskRepository: ToDoTaskRepository = PlaceholderToDoTaskRepository()
    private val idFactory: IdFactory = UUIDIdFactory()

    override suspend fun index(): MutableList<ToDoList> {
        toDoLists.forEach {
            loadTasksFor(it)
        }
        return toDoLists
    }

    override suspend fun getById(id: Id): ToDoList? {
        return toDoLists.firstOrNull { it.id == id }
    }

    override fun create(name: String, dueAt: Date): ToDoList {
        return ToDoList(idFactory.makeId(), name, ArrayList(), dueAt, Date(), Date())
    }

    override suspend fun store(toDoList: ToDoList) {
        val pos = toDoLists.indexOf(toDoList)
        if (pos < 0) {
            toDoLists.add(toDoList)
        } else {
            toDoLists[pos] = toDoList
        }
    }

    override suspend fun delete(toDoList: ToDoList) {
        toDoLists.remove(toDoList)
    }

    private  suspend fun loadTasksFor(toDoList: ToDoList) {
        toDoList.toDoTasks = toDoTaskRepository.indexFor(toDoList)
    }


    companion object {
        private val toDoLists: MutableList<ToDoList> = ArrayList()

    }
}