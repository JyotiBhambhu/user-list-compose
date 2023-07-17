package com.jyoti.auth.data.repo

import com.jyoti.auth.data.data_source.AuthRemoteDataSource
import com.jyoti.auth.data.model.AuthRequestModel
import com.jyoti.auth.domain.repo.AuthRepo
import com.jyoti.core.data.data_source.TokenLocalDataSource
import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.extensions.emitAsFlow
import com.jyoti.core.extensions.onEachSuccess
import com.jyoti.core.network.ApiCaller
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AuthRepoImpl constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val apiCaller: ApiCaller,
) : AuthRepo {

    override fun doLogin(email: String, password: String) =
        apiCaller.emitAsFlow {
            authRemoteDataSource.loginUser(AuthRequestModel(email = email, password = password))
        }.onEachSuccess { responseModel ->
            responseModel?.data?.let { tokenLocalDataSource.saveAccessToken(it) }
        }.flowOn(ioDispatcher)

    override fun doSignUp(email: String, password: String) = apiCaller.emitAsFlow {
        authRemoteDataSource.signUpUser(AuthRequestModel(email = email, password = password))
    }.flowOn(ioDispatcher)

    suspend fun nukeToken() = withContext(ioDispatcher) {
        tokenLocalDataSource.nukeAccessToken()
    }
}