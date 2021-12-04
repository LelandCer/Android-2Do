package com.lelandcer.twodo.domain

import com.lelandcer.twodo.domain.actions.Action
import com.lelandcer.twodo.domain.actions.ActionParameters
import com.lelandcer.twodo.domain.actions.ActionReturnData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionHandler(val scope: CoroutineScope) {

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