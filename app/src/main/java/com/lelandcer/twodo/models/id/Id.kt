package com.lelandcer.twodo.models.id

/**
 * Represents a unique identifier for a model
 */
abstract class Id {
    abstract fun getKey(): String

    override fun equals(other: Any?): Boolean =
        other is Id && getKey()== other.getKey()

    override fun hashCode(): Int {
        return getKey().hashCode()
    }
}