package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

class SaveToDoList @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<SaveToDoList.Parameters, SaveToDoList.ReturnData> {

    override suspend fun execute(params: Parameters): ReturnData {
        toDoListRepository.store(params.toDoList)
        return ReturnData()
    }

    companion object {
        fun getParameters(toDoList: ToDoList) = Parameters(toDoList)
    }

    class Parameters(val toDoList: ToDoList) : ActionParameters
    class ReturnData : ActionReturnData

}