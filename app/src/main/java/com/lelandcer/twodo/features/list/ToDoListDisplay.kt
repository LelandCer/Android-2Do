package com.lelandcer.twodo.features.list

import com.lelandcer.twodo.models.list.ToDoList

/** Converts a ToDoList into Displayable strings */
interface ToDoListDisplay {
    fun forToDoList(toDoList: ToDoList):  ToDoListDisplay
    fun completionRatio(): String
    fun name(): String
    fun dueAt(): String
    fun dueAtDateFormat():String
}