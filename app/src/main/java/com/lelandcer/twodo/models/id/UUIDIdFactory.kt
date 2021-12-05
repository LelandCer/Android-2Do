package com.lelandcer.twodo.models.id

import java.util.*
import javax.inject.Inject

/** Factory for making Id backed by UUID format */
class UUIDIdFactory @Inject constructor() : IdFactory {
    override fun makeId(): Id {
        return UUIDId(UUID.randomUUID())
    }
}