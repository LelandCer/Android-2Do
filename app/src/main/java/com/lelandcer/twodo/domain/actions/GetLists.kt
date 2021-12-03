package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

class GetLists @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<GetLists.Parameters, GetLists.ReturnData> {

    override suspend fun execute(params: Parameters): ReturnData {
        val lists = toDoListRepository.index()
        lists.forEach {
            it.toDoTasks = toDoTaskRepository.indexFor(it)
        }
        return ReturnData(lists)
    }

    companion object {
        fun getParameters() = Parameters()
    }

    class Parameters : ActionParameters
    class ReturnData(val toDoLists: Collection<ToDoList>) : ActionReturnData
}