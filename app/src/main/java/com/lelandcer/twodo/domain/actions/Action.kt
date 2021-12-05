package com.lelandcer.twodo.domain.actions

/** Interface for executable actions. Actions allow for business logic to be treated a handleable objects through a unified service */
interface Action<V : ActionParameters, R : ActionReturnData> {

    /** Execute this action for the given params */
    suspend fun execute(params: V): R
}

interface ActionReturnData
interface ActionParameters