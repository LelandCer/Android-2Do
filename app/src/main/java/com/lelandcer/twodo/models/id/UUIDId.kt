package com.lelandcer.twodo.models.id

import java.util.*

/** An Id that is backed by UUID */
class UUIDId(private val uuid: UUID) : Id() {
    override fun getKey(): String {
        return uuid.toString()
    }

}