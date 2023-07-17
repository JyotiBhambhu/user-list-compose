package com.jyoti.auth.login.ui.redux

import com.jyoti.auth.login.ui.model.LoginUIModel
import com.jyoti.core.base.ReduceAction

sealed class LoginReduceAction : ReduceAction {
    object LoginStarted : LoginReduceAction()
    data class LoginError(val message: String) : LoginReduceAction()
    data class LoginSuccess(val uiModel: LoginUIModel) : LoginReduceAction()
}