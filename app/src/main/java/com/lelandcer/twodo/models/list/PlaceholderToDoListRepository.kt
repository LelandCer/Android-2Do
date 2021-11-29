package com.lelandcer.twodo.models.list

import android.util.Log
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
    private val idFactory: IdFactory = UUIDIdFactory()

    init {

        createPlaceholder("Important")
        createPlaceholder("First")
        createPlaceholder("Second")
        createPlaceholder("Third")
        createPlaceholder("Do this!!!!")
        createPlaceholder("Whenever")
        createPlaceholder("Ooops put in way to long of a description, maybe I should add some kind of limit to this")
        createPlaceholder("short")
        createPlaceholder("\uD83D\uDE1C")
        createPlaceholder("Whenever")

    }

    private fun createPlaceholder(name: String) {
        val list = create(name, randomDate())
        addPlaceholderTasks(list)
        store(list)

    }

    private fun randomDate(): Date {
        // Randomly generate a date, distributed around today, with some in the future and some the past
        val now = Date().time
        val daysInMillis = 1000 * 60 * 60 * 24;
        val days = Random().nextInt(5) - 1
        if (days > 3) Random().nextInt(300) + 3
        return Date(now + days * daysInMillis)
    }

    override fun index(): MutableList<ToDoList> {
        toDoLists.forEach {
            loadTasksFor(it)
        }
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

    private fun loadTasksFor(toDoList: ToDoList) {
        toDoList.toDoTasks = toDoTaskRepository.indexFor(toDoList)
    }

    private fun addPlaceholderTasks(toDoList: ToDoList) {
        val numberOfTasks = Random().nextInt(10)
        for (i in 0..numberOfTasks) {
            val task = toDoTaskRepository.create(toDoList, "Need to do: " + Random().nextLong())
            if (Random().nextBoolean()) {
                task.complete()
            }

            toDoTaskRepository.store(toDoList, task)
        }
    }

    companion object {
        private val toDoLists: MutableList<ToDoList> = ArrayList()

    }
}