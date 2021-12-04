package com.lelandcer.twodo.domain.actions

import com.lelandcer.twodo.models.list.ToDoList
import com.lelandcer.twodo.models.list.repositories.ToDoListRepository
import com.lelandcer.twodo.models.task.repositories.ToDoTaskRepository
import javax.inject.Inject

/** An action that will fetch all the ToDoLists */
class GetLists @Inject constructor(
    val toDoListRepository: ToDoListRepository,
    val toDoTaskRepository: ToDoTaskRepository
) : Action<GetLists.Parameters, GetLists.ReturnData> {

    override suspend fun execute(params: Parameters): ReturnData {
        // Fetch all the lists
        val lists = toDoListRepository.index()

        // Load the tasks into each list
        // Note, it is possible for the repository to do this automatically for a list
        // However this allows the repositories to be completely separate from each other
        lists.forEach {
            it.toDoTasks = ArrayList(toDoTaskRepository.indexFor(it))
        }
        return ReturnData(lists)
    }

    companion object {
        fun getParameters() = Parameters()
    }

    class Parameters : ActionParameters
    class ReturnData(val toDoLists: Collection<ToDoList>) : ActionReturnData
}