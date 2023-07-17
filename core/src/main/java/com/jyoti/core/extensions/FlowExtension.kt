package com.jyoti.core.extensions

import com.jyoti.core.network.SealedResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<SealedResult<T>>.onEachSuccess(action: suspend (T) -> Unit): Flow<SealedResult<T>> =
    transform { value ->
        (value as? SealedResult.Response)?.response?.let { safeValue ->
            action(safeValue)
        }
        return@transform emit(value)
    }
