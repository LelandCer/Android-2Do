package com.lelandcer.twodo.domain

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTaskRepository
import javax.inject.Inject

class GetLists @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) {

    suspend fun execute(): Collection<ToDoList> {
        val lists = toDoListRepository.index()
        lists.forEach {
            it.toDoTasks = toDoTaskRepository.indexFor(it)
        }
        return lists
    }
}