package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.task.Task
import java.util.*

class List(
    var id: Id,
    var name: String,
    var tasks: MutableCollection<Task>,
    var dueAt: Date,
    var createdAt: Date,
    var updatedAt: Date
) {

    override fun equals(other: Any?): Boolean =
        other is List && id == other.id

    override fun hashCode(): Int {
        return id.hashCode()
    }

}