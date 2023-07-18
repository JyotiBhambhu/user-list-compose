package com.jyoti.core.base

import com.jyoti.core.network.SealedResult

abstract class BaseMapper<T, R> {

    abstract fun mapper(inValue: T?): R?

    /**
     * Bridge between response and ui models to transform models on use case.
     */
    fun mapSealedResult(inResult: SealedResult<T?>): SealedResult<R?> {
        return when (inResult) {
            is SealedResult.Response -> SealedResult.Response(
                mapper(inResult.response)
            )
            is SealedResult.Error -> inResult
            is SealedResult.Unknown -> inResult
            is SealedResult.Ignore -> inResult
        }
    }
}
