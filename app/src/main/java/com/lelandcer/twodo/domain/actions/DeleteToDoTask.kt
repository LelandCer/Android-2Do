package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.ToDoTask
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

class DeleteToDoTask @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<DeleteToDoTask.Parameters, DeleteToDoTask.ReturnData> {


    override suspend fun execute(params: Parameters): ReturnData {
        toDoTaskRepository.delete(params.toDoTask)
        return ReturnData()
    }

    companion object {
        fun getParameters(toDoTask: ToDoTask) = Parameters(toDoTask)
    }

    class Parameters(val toDoTask: ToDoTask) : ActionParameters
    class ReturnData : ActionReturnData


}