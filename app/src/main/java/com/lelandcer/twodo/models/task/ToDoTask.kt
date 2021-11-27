package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.Id
import java.util.*

class ToDoTask(
    var id: Id,
    var listId: Id,
    var name: String,
) {
    var isCompleted: Boolean = false
    private set

    var completedAt: Date? = null
    private set

    override fun equals(other: Any?): Boolean =
        other is ToDoTask && id == other.id

    override fun hashCode(): Int {
        return id.hashCode()
    }


    fun complete() {
        isCompleted = true
        completedAt = Date()
    }

    fun unComplete() {
        isCompleted = false
        completedAt = null
    }

}