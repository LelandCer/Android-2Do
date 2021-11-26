package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.Id
import java.util.*

class Task(
    var id: Id,
    var listId: Id,
    var name: String,
    var isCompleted: Boolean = false,
    var completedAt: Date? = null
) {

    override fun equals(other: Any?): Boolean =
        other is Task && id == other.id

    override fun hashCode(): Int {
        return id.hashCode()
    }

}