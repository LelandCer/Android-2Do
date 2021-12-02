package com.lelandcer.twodo.domain

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListFactory
import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTaskFactory
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

class CreatePlaceholderData @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
    private val toDoTaskRepository: ToDoTaskRepository,
    private val toDoListFactory: ToDoListFactory,
    private val toDoTaskFactory: ToDoTaskFactory) {

    fun create() {
        runBlocking {
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
    }


    private suspend fun addPlaceholderTasks(toDoList: ToDoList) {
        val numberOfTasks = Random().nextInt(10)
        for (i in 0..numberOfTasks) {
            val task = toDoTaskFactory.makeToDoTask(toDoList, "Need to do: " + Random().nextLong())
            if (Random().nextBoolean()) {
                task.complete()
            }
            toDoList.toDoTasks.add(task)

            toDoTaskRepository.store(toDoList, task)
        }
    }


    private suspend fun createPlaceholder(name: String) {
        val list = toDoListFactory.makeToDoList(name, randomDate())
        addPlaceholderTasks(list)
        toDoListRepository.store(list)

    }

    private fun randomDate(): Date {
        // Randomly generate a date, distributed around today, with some in the future and some the past
        val now = Date().time
        val daysInMillis = 1000 * 60 * 60 * 24;
        val days = Random().nextInt(5) - 1
        if (days > 3) Random().nextInt(300) + 3
        return Date(now + days * daysInMillis)
    }

}