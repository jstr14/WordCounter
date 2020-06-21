package com.hector.wordcounter.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


abstract class Interactor<out SuccessValue, in Parameters> {

    fun execute(scope: CoroutineScope, parameters: Parameters, delegate: (result: Result<SuccessValue, *>) -> Unit) =
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                run(parameters)
            }
            delegate(result)
        }

    abstract fun run(params: Parameters): Result<SuccessValue, *>
}
