package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

class ToDoTaskFactory @Inject constructor(private val idFactory: IdFactory) {

    fun makeToDoTask(toDoList: ToDoList, name: String): ToDoTask {
        return ToDoTask(idFactory.makeId(), toDoList.id, name)
    }
}