package com.jyoti.core.network

import com.jyoti.core.base.BaseResponseModel
import com.jyoti.core.network.ErrorUtils.parseError
import retrofit2.Response
import javax.inject.Inject

class ApiCaller @Inject constructor() {

    /**
     * Generic api call handler to transform typical response to [SealedResult.Response].
     *
     * Handled errors are mapped into [SealedResult.Error].
     *
     * UnHandled errors are mapped into [SealedResult.Unknown].
     *
     */
    suspend operator fun <T : BaseResponseModel<T>> invoke(call: suspend () -> Response<T>): SealedResult<T?> {
        return try {
            val response = call()
            return when (response.isSuccessful) {
                true -> {
                    if (response.code() == StatusCode.SC_NO_CONTENT.code) {
                        SealedResult.Ignore
                    } else if (response.code() == StatusCode.SC_SUCCESS.code) {
                        SealedResult.Response(response.body())
                    } else {
                        response.body()?.error?.let {
                            SealedResult.Error(
                                it
                            )
                        } ?: SealedResult.Unknown
                    }
                }
                false -> {
                    val apiError = parseError(response)
                    return apiError.error?.let { SealedResult.Error(it) } ?: SealedResult.Unknown
                }
            }
        } catch (exception: Exception) {
            SealedResult.Unknown
        }
    }
}
