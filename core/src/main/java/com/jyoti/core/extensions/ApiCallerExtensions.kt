package com.jyoti.core.extensions

import com.jyoti.core.base.BaseResponseModel
import com.jyoti.core.network.ApiCaller
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T : BaseResponseModel<T>> ApiCaller.emitAsFlow(
    call: suspend () -> Response<T>
) = flow {
    emit(this@emitAsFlow.invoke(call = { call() }))
}
