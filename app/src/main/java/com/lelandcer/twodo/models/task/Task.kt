package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.Id
import java.util.*

class Task(
    var id: Id,
    var name: String,
    var isCompleted: Boolean,
    var completedAt: Date?
) {


}