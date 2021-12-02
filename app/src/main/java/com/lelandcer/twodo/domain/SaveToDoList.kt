package com.lelandcer.twodo.domain

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

class SaveToDoList @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) {

    suspend fun execute(toDoList: ToDoList): MutableCollection<ToDoList> {
        toDoListRepository.store(toDoList)
        return toDoListRepository.index()
    }

}