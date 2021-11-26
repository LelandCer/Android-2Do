package com.lelandcer.twodo.models.id

import java.util.*

class UUIDId(private val uuid: UUID) : Id {
    override fun getKey(): String {
        return uuid.toString()
    }

    override fun equals(other: Any?): Boolean =
        other is Id && uuid.toString() == other.getKey()

}