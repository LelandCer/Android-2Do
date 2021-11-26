package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import com.lelandcer.twodo.models.id.IdFactory
import com.lelandcer.twodo.models.id.UUIDFactory
import com.lelandcer.twodo.models.task.PlaceholderTaskRepository
import com.lelandcer.twodo.models.task.TaskRepository
import java.util.*
import kotlin.collections.ArrayList

/** A static placeholder repository for lists
 * TODO remove once implemented */
class PlaceholderListRepository : ListRepository {
    private val taskRepository: TaskRepository = PlaceholderTaskRepository()
    private val lists: MutableList<List>
    private val idFactory: IdFactory = UUIDFactory()

    init {
        lists = ArrayList()
        var list = create("First", Date())
        addPlaceholderTasks(list)
        list = create("Second", Date())
        addPlaceholderTasks(list)

        list = create("Third", Date())
        addPlaceholderTasks(list)

        list = create("Do this!!!!", Date())
        addPlaceholderTasks(list)

        list = create("Important", Date())
        addPlaceholderTasks(list)

        list = create("Whenever", Date())
        addPlaceholderTasks(list)

    }

    override fun index(): MutableList<List> {
        return lists
    }

    override fun getById(id: Id): List? {
        return lists.firstOrNull { it.id == id }
    }

    override fun create(name: String, dueAt: Date): List {
        val newList = List(idFactory.makeId(), name, ArrayList(), dueAt, Date(), Date())
        lists.add(newList)
        return newList
    }

    override fun store(list: List) {
        val pos = lists.indexOf(list)
        if (pos < 0) {
            lists.add(list)
        } else {
            lists[pos] = list
        }
    }

    override fun delete(list: List) {
        lists.remove(list)
    }

    private fun addPlaceholderTasks(list: List) {
        val numberOfTasks = Random().nextInt(10)
        for ( i in 0 .. numberOfTasks) {
            taskRepository.create(list, "Need to do: " + Random().nextLong())
        }

    }
}