package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

/** An action that will save a ToDoList */
class SaveToDoList @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<SaveToDoList.Parameters, SaveToDoList.ReturnData> {

    override suspend fun execute(params: Parameters): ReturnData {
        toDoListRepository.store(params.toDoList)

        // Note, ToDoTasks do not get persisted this way. They must be saved through their own action
        return ReturnData()
    }

    companion object {
        fun getParameters(toDoList: ToDoList) = Parameters(toDoList)
    }

    class Parameters(val toDoList: ToDoList) : ActionParameters
    class ReturnData : ActionReturnData

}