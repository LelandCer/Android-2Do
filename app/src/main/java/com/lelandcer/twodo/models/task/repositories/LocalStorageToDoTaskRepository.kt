package com.lelandcer.twodo.models.task.repositories


/** An interface for ToDoTaskRepository.
 *  Identifies repository implementations that save items to Disk, have heavier costs, and unique failure states
 */
interface LocalStorageToDoTaskRepository : ToDoTaskRepository