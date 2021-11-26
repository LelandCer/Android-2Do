package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.task.Task
import java.util.*

class List(
    var id: String,
    var name: String,
    var tasks: MutableCollection<Task>,
    var dueAt: Date,
    var createdAt: Date,
    var updatedAt: Date
) {


}