package com.jyoti.auth.domain.repo

import com.jyoti.auth.data.model.AuthResponseModel
import com.jyoti.core.network.SealedResult
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun doLogin(email: String, password: String): Flow<SealedResult<AuthResponseModel?>>
    fun doSignUp(email: String, password: String): Flow<SealedResult<AuthResponseModel?>>
}