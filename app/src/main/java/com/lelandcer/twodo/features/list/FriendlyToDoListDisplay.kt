package com.lelandcer.twodo.features.list

import com.lelandcer.twodo.models.list.ToDoList
import javax.inject.Inject

/** A display implementation for ToDoList that is less "business" and more "fun" */
class FriendlyToDoListDisplay @Inject constructor(): ToDoListDisplay {
    private var toDoList: ToDoList? = null
    override fun forToDoLIst(toDoList: ToDoList): ToDoListDisplay {
        this.toDoList = toDoList
        return this
    }

    override fun completionRatio(): String {
        return "1/1"
    }

    override fun name(): String {
        return toDoList?.name?:  ""
    }

    override fun dueAt(): String {
        return "Soon"
    }

    override fun dueAtDateFormat(): String {
        return "11/2"
    }
}