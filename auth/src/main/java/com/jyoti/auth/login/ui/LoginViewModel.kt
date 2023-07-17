package com.jyoti.auth.login.ui

import androidx.lifecycle.viewModelScope
import com.jyoti.auth.domain.use_case.LoginUseCase
import com.jyoti.auth.login.ui.model.LoginUIModel
import com.jyoti.auth.login.ui.redux.LoginIntent
import com.jyoti.auth.login.ui.redux.LoginReduceAction
import com.jyoti.auth.login.ui.redux.LoginViewState
import com.jyoti.core.base.LoadState
import com.jyoti.core.base.MviViewModel
import com.jyoti.core.di.DispatcherModule
import com.jyoti.core.network.SealedResult
import com.jyoti.core.util.toEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    @DispatcherModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
) :
    MviViewModel<LoginViewState, LoginIntent, LoginReduceAction>(initialState = LoginViewState.initial) {

    private fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch(mainDispatcher) {
            loginUseCase(email, password)
            when (val sealedResult = loginUseCase(email, password).first()) {
                is SealedResult.Error -> handle(LoginReduceAction.LoginError(sealedResult.error))
                is SealedResult.Response -> handle(
                    LoginReduceAction.LoginSuccess(
                        sealedResult.response ?: LoginUIModel()
                    )
                )
                SealedResult.Unknown -> handle(LoginReduceAction.LoginError(""))
                SealedResult.Ignore -> {}
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.matches(emailRegex)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.trim().length > 4
    }

    override fun executeIntent(mviIntent: LoginIntent) {
        when (mviIntent) {
            is LoginIntent.DoLogin -> {

                handle(LoginReduceAction.EmailError(!isValidEmail(mviIntent.email)))
                handle(LoginReduceAction.PasswordError(!isValidPassword(mviIntent.password)))

                if (isValidEmail(mviIntent.email) && isValidPassword(mviIntent.password)) {
                    handle(LoginReduceAction.LoginStarted)
                    loginUser(mviIntent.email, mviIntent.password)
                }
            }
        }
    }

    override fun reduce(state: LoginViewState, reduceAction: LoginReduceAction): LoginViewState =
        when (reduceAction) {
            is LoginReduceAction.LoginError -> {
                state.copy(loadState = LoadState.ERROR, loginError = reduceAction.message)
            }
            LoginReduceAction.LoginStarted -> {
                state.copy(loadState = LoadState.LOADING)
            }
            is LoginReduceAction.LoginSuccess -> {
                state.copy(loadState = LoadState.LOADED, navigateToHome = true.toEvent())
            }
            is LoginReduceAction.EmailError -> {
                state.copy(emailError = reduceAction.isError)
            }
            is LoginReduceAction.PasswordError -> {
                state.copy(passwordError = reduceAction.isError)
            }
        }
}