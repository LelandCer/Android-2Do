package com.lelandcer.twodo.domain

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import javax.inject.Inject

class DeleteToDoList @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) {

    suspend fun execute(toDoList: ToDoList) {
        toDoListRepository.delete(toDoList )
        val tasks = toDoTaskRepository.indexFor(toDoList)
        tasks.forEach {
            toDoTaskRepository.delete(it)
        }
    }
}