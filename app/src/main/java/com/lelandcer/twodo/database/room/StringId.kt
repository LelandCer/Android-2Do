package com.lelandcer.twodo.database.room

import com.lelandcer.twodo.models.id.Id

class StringId(private val id: String) : Id() {
    override fun getKey(): String {
        return id
    }
}