package com.lelandcer.twodo.domain.actions

interface Action<V : ActionParameters, R : ActionReturnData> {

    suspend fun execute(params: V): R
}

interface ActionReturnData
interface ActionParameters