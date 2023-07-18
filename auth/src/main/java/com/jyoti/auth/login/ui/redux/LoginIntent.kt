package com.jyoti.auth.login.ui.redux

import com.jyoti.core.base.MviIntent

sealed class LoginIntent : MviIntent {
    data class DoLogin(val email: String, val password: String) : LoginIntent()
}