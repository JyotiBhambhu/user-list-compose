package com.jyoti.auth.signup.ui.redux

import com.jyoti.auth.login.ui.model.LoginUIModel
import com.jyoti.auth.signup.ui.model.SignUpUIModel
import com.jyoti.core.base.ReduceAction

sealed class SignUpReduceAction : ReduceAction {
    data class EmailError(val isError: Boolean) : SignUpReduceAction()
    data class PasswordError(val isError: Boolean) : SignUpReduceAction()
    data class ConfirmPasswordError(val isError: Boolean) : SignUpReduceAction()
    object SignUpStarted : SignUpReduceAction()
    data class SignUpError(val message: String) : SignUpReduceAction()
    data class SignUpSuccess(val uiModel: SignUpUIModel) : SignUpReduceAction()
}