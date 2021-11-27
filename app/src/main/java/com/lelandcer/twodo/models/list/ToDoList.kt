package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.task.ToDoTask
import java.util.*

class ToDoList(
    var id: Id,
    var name: String,
    var toDoTasks: MutableCollection<ToDoTask>,
    var dueAt: Date,
    var createdAt: Date,
    var updatedAt: Date
) {

    override fun equals(other: Any?): Boolean =
        other is ToDoList && id == other.id

    override fun hashCode(): Int {
        return id.hashCode()
    }

    /** Return the count for the total amount of tasks */
    fun taskTotalCount(): Int {
        return toDoTasks.size
    }

    /** Return the count for the tasks that are complete */
    fun taskCompletedCount(): Int {
        return toDoTasks.filter { toDoTask -> toDoTask.isCompleted  }.count()
    }

    /** Return true if all tasks are marked complete */
    fun isCompleted(): Boolean {
        return toDoTasks.none { toDoTask -> !toDoTask.isCompleted }

    }

}