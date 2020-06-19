package com.hector.wordcounter.domain

import kotlinx.coroutines.*


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
