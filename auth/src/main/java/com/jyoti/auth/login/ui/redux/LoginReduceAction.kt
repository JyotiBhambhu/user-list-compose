package com.jyoti.auth.login.ui.redux

import com.jyoti.auth.login.ui.model.LoginUIModel
import com.jyoti.core.base.ReduceAction

sealed class LoginReduceAction : ReduceAction {
    data class EmailError(val isError: Boolean) : LoginReduceAction()
    data class PasswordError(val isError: Boolean) : LoginReduceAction()
    object LoginStarted : LoginReduceAction()
    data class LoginError(val message: String) : LoginReduceAction()
    data class LoginSuccess(val uiModel: LoginUIModel) : LoginReduceAction()
}