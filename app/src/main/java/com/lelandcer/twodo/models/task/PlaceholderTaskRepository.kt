package com.lelandcer.twodo.models.task

import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDFactory
import com.lelandcer.twodo.models.list.List

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderTaskRepository : TaskRepository {
    private val idFactory: IdFactory = UUIDFactory()
    override fun indexFor(list: List): MutableCollection<Task> {
        return list.tasks
    }

    override fun store(list: List, task: Task) {
        list.tasks.add(task)
    }

    override fun create(list: List, name: String): Task {
        return Task(idFactory.makeId(), name)
    }

    override fun delete(task: Task) {
        TODO("Not yet implemented")
    }
}