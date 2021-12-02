package com.lelandcer.twodo.domain

import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import javax.inject.Inject

class SaveToDoTask @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) {

    suspend fun execute(toDoTask: ToDoTask) {
        val toDoList = toDoListRepository.getById(toDoTask.listId)
        if (toDoList != null) {
            toDoTaskRepository.store(toDoList, toDoTask)
        }

    }
}