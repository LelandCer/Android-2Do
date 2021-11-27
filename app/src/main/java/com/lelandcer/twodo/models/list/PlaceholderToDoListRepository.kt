package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDIdFactory
import com.lelandcer.twodo.models.task.PlaceholderToDoTaskRepository
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderToDoListRepository @Inject constructor() : ToDoListRepository {
    private val toDoTaskRepository: ToDoTaskRepository = PlaceholderToDoTaskRepository()
    private val toDoLists: MutableList<ToDoList>
    private val idFactory: IdFactory = UUIDIdFactory()

    init {
        toDoLists = ArrayList()

        createPlaceholder("Important")
        createPlaceholder("First")
        createPlaceholder("Second")
        createPlaceholder("Third")
        createPlaceholder("Do this!!!!")
        createPlaceholder("Whenever")

    }

    private fun createPlaceholder(name: String) {
        val list = create(name, Date())
        addPlaceholderTasks(list)
        store(list)

    }

    override fun index(): MutableList<ToDoList> {
        return toDoLists
    }

    override fun getById(id: Id): ToDoList? {
        return toDoLists.firstOrNull { it.id == id }
    }

    override fun create(name: String, dueAt: Date): ToDoList {
        return ToDoList(idFactory.makeId(), name, ArrayList(), dueAt, Date(), Date())
    }

    override fun store(toDoList: ToDoList) {
        val pos = toDoLists.indexOf(toDoList)
        if (pos < 0) {
            toDoLists.add(toDoList)
        } else {
            toDoLists[pos] = toDoList
        }
    }

    override fun delete(toDoList: ToDoList) {
        toDoLists.remove(toDoList)
    }

    private fun addPlaceholderTasks(toDoList: ToDoList) {
        val numberOfTasks = Random().nextInt(10)
        for (i in 0..numberOfTasks) {
            val task = toDoTaskRepository.create(toDoList, "Need to do: " + Random().nextLong())
            toDoTaskRepository.store(toDoList, task)
        }

    }
}