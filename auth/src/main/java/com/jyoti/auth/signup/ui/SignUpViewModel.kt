package com.jyoti.auth.signup.ui

import androidx.lifecycle.viewModelScope
import com.jyoti.auth.domain.use_case.SignUpUseCase
import com.jyoti.auth.signup.ui.model.SignUpUIModel
import com.jyoti.auth.signup.ui.redux.SignUpIntent
import com.jyoti.auth.signup.ui.redux.SignUpReduceAction
import com.jyoti.auth.signup.ui.redux.SignUpViewState
import com.jyoti.auth.util.isPasswordMatch
import com.jyoti.auth.util.isValidEmail
import com.jyoti.auth.util.isValidPassword
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
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @DispatcherModule.MainDispatcher private val mainDispatcher: CoroutineDispatcher
) :
    MviViewModel<SignUpViewState, SignUpIntent, SignUpReduceAction>(initialState = SignUpViewState.initial) {

    private fun signUpUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(mainDispatcher) {
            when (val sealedResult = signUpUseCase(email, password).first()) {
                is SealedResult.Error -> handle(SignUpReduceAction.SignUpError(sealedResult.error))
                is SealedResult.Response -> handle(
                    SignUpReduceAction.SignUpSuccess(
                        sealedResult.response ?: SignUpUIModel()
                    )
                )
                SealedResult.Unknown -> handle(SignUpReduceAction.SignUpError(""))
                SealedResult.Ignore -> {}
            }
        }
    }

    override fun executeIntent(mviIntent: SignUpIntent) {
        when (mviIntent) {
            is SignUpIntent.DoSignUp -> {

                handle(SignUpReduceAction.EmailError(!mviIntent.email.isValidEmail()))
                handle(SignUpReduceAction.PasswordError(!mviIntent.password.isValidPassword()))
                handle(
                    SignUpReduceAction.ConfirmPasswordError(
                        !mviIntent.password.isPasswordMatch(
                            mviIntent.confirmPassword
                        )
                    )
                )

                if (mviIntent.email.isValidEmail()
                    && mviIntent.password.isValidPassword()
                    && mviIntent.password.isPasswordMatch(
                        mviIntent.confirmPassword
                    )
                ) {
                    handle(SignUpReduceAction.SignUpStarted)
                    signUpUser(mviIntent.email, mviIntent.password)
                }
            }
        }
    }

    override fun reduce(state: SignUpViewState, reduceAction: SignUpReduceAction): SignUpViewState =
        when (reduceAction) {
            is SignUpReduceAction.SignUpError -> {
                state.copy(loadState = LoadState.ERROR, signUpError = reduceAction.message)
            }
            SignUpReduceAction.SignUpStarted -> {
                state.copy(loadState = LoadState.LOADING)
            }
            is SignUpReduceAction.SignUpSuccess -> {
                state.copy(loadState = LoadState.LOADED, navigateUp = true.toEvent())
            }
            is SignUpReduceAction.EmailError -> {
                state.copy(emailError = reduceAction.isError)
            }
            is SignUpReduceAction.PasswordError -> {
                state.copy(passwordError = reduceAction.isError)
            }
            is SignUpReduceAction.ConfirmPasswordError -> {
                state.copy(confirmPasswordError = reduceAction.isError)
            }
        }
}