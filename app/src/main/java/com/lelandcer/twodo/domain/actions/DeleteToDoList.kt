package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

class DeleteToDoList @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<DeleteToDoList.Parameters, DeleteToDoList.ReturnData> {

    override suspend fun execute(params: Parameters): ReturnData {
        val toDoList = params.toDoList
        toDoListRepository.delete(toDoList)
        toDoTaskRepository.deleteAll(toDoList)
        return ReturnData()
    }

    companion object {
        fun getParameters(toDoList: ToDoList) = Parameters(toDoList)
    }

    class Parameters(val toDoList: ToDoList) : ActionParameters
    class ReturnData : ActionReturnData
}