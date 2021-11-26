package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.list.List
import java.util.*

class Task(
    var id: Id,
    var name: String,
    var isCompleted: Boolean,
    var completedAt: Date?
) {

    override fun equals(other: Any?): Boolean =
        other is Task && id == other.id

}