package com.lelandcer.twodo.models.id

/**
 * Factory interface for making new Ids
 */
interface IdFactory {

    fun makeId(): Id
}