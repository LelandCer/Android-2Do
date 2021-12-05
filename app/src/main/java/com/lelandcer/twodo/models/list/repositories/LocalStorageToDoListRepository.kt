package com.lelandcer.twodo.models.list.repositories


/**
 * An interface for ToDoListRepository.
 *  Identifies repository implementations that save items to Disk, have heavier costs, and unique failure states
 */
interface LocalStorageToDoListRepository : ToDoListRepository