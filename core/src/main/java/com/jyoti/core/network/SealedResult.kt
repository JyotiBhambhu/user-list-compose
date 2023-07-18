package com.jyoti.core.network

/**
 * Sealed class to hold response for both success and fail cases
 */
sealed class SealedResult<out T> {
    data class Response<out T>(
        val response: T,
        val error: String = ""
    ) : SealedResult<T>()

    data class Error(val error: String) : SealedResult<Nothing>()
    object Unknown : SealedResult<Nothing>()
    object Ignore : SealedResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Response<*> -> "Success[data=$response]"
            is Error -> "Error[error=$error]"
            is Ignore -> "Ignored"
            Unknown -> "Unknown"
        }
    }
}
