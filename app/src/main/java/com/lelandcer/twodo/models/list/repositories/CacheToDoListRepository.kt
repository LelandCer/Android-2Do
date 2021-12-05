package com.lelandcer.twodo.models.list.repositories

import com.lelandcer.twodo.models.list.ToDoList

/**
 * An interface for ToDoListRepository.
 *  Identifies repository implementations that are non-persistent between app launches, but have high responsiveness
 */
interface CacheToDoListRepository : ToDoListRepository {

    /** Initializes the cache with a complete set of data. */
    fun storeAll(toDoLists: Collection<ToDoList>)

}