package com.lelandcer.twodo.models.id

/**
 * Represents a unique identifier for a model
 */
abstract class Id {
    /** The key identifier for the Id */
    abstract fun getKey(): String

    /** If any two keys are equal than the Ids are equal regardless of type */
    override fun equals(other: Any?): Boolean =
        other is Id && getKey() == other.getKey()

    override fun hashCode(): Int {
        // Use the string Hashcode, not the underlying implementation hashcode
        // Otherwise two Id could have the same key but different hashcodes
        return getKey().hashCode()
    }
}