package com.lelandcer.twodo.domain

import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import javax.inject.Inject

class DeleteToDoTask @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) {

    suspend fun execute(toDoTask: ToDoTask) {
        toDoTaskRepository.delete(toDoTask)
    }
}