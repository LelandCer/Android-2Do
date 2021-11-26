package com.lelandcer.twodo.models.id

import java.util.*

/** Factory for making Id backed by UUID format */
class UUIDIdFactory: IdFactory {
    override fun makeId(): Id {
        return UUIDId(UUID.randomUUID())
    }
}