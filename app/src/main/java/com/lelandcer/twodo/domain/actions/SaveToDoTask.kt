package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

class SaveToDoTask @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<SaveToDoTask.Parameters, SaveToDoTask.ReturnData> {


    override suspend fun execute(params: Parameters): ReturnData {
        val toDoTask = params.toDoTask
        val toDoList = toDoListRepository.getById(toDoTask.listId)
        if (toDoList != null) {
            toDoTaskRepository.store(toDoList, toDoTask)
        }
        return ReturnData()
    }

    companion object {
        fun getParameters(toDoTask: ToDoTask) = Parameters(toDoTask)
    }

    class Parameters(val toDoTask: ToDoTask) : ActionParameters
    class ReturnData : ActionReturnData


}