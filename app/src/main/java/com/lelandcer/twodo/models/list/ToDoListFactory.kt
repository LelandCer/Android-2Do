package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.IdFactory
import java.util.*
import javax.inject.Inject

class ToDoListFactory @Inject constructor(private val idFactory: IdFactory) {

    fun makeToDoList(name: String, dueAt: Date): ToDoList {
        return ToDoList(idFactory.makeId(), name, ArrayList(), dueAt, Date(), Date())
    }
}