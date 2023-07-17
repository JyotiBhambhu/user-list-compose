package com.jyoti.auth.data.data_source

import com.jyoti.auth.data.model.AuthRequestModel
import com.jyoti.auth.data.model.AuthResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteDataSource {

    @POST(ENDPOINT_LOGIN)
    suspend fun loginUser(
        @Body loginRequestModel: AuthRequestModel
    ): Response<AuthResponseModel>

    @POST(ENDPOINT_SIGN_UP)
    suspend fun signUpUser(
        @Body signUpRequestModel: AuthRequestModel
    ): Response<AuthResponseModel>

    companion object {
        private const val ENDPOINT_LOGIN = "/api/login"
        private const val ENDPOINT_SIGN_UP = "/api/register"
    }
}
