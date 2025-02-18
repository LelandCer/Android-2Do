package com.lelandcer.twodo.database.room.models

import com.lelandcer.twodo.models.id.Id

/** A simple serializable Id that just takes and returns a string for a key. Equals still works for all types of Id */
class StringId(private val id: String) : Id() {
    override fun getKey(): String {
        return id
    }
}