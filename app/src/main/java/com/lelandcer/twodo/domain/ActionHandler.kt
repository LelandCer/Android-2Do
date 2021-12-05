package com.lelandcer.twodo.domain

import com.lelandcer.twodo.domain.actions.Action
import com.lelandcer.twodo.domain.actions.ActionParameters
import com.lelandcer.twodo.domain.actions.ActionReturnData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** Executes actions on the provided scope. This uses the IO context, but different handlers could use different threading implementations */
class ActionHandler(private val scope: CoroutineScope) {

    /** Performs the provided action, with the provided params */
    fun <V : ActionParameters, R : ActionReturnData> perform(
        action: Action<V, R>,
        params: V,
        callback: ActionHandlerCallback<R>
    ) {

        scope.launch(Dispatchers.IO) {

            val response = action.execute(params)
            callback.complete(response)
        }


    }

    fun interface ActionHandlerCallback<R : ActionReturnData> {
        fun complete(t: R)
    }
}