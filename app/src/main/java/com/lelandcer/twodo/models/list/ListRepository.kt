package com.lelandcer.twodo.models.list

import com.lelandcer.twodo.models.id.Id
import java.util.*

/** Manages the storage and retrieval of Lists */
interface ListRepository {

    /**
     * Get all Lists
     * Note: no pagination
     * TODO add either limitations or pagination
     */
    fun index(): MutableCollection<List>

    /**
     * Get a specific List for a provided Id
     */
    fun getById(id: Id): List?

    /**
     * Create and store a new List
     */
    fun create(name: String, dueAt: Date): List

    /**
     * Store a List
     */
    fun store(list: List)

    /**
     * Delete a List from storage
     */
    fun delete(list: List)
}