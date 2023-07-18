package com.jyoti.auth.signup.ui.redux

import com.jyoti.core.base.MviIntent

sealed class SignUpIntent : MviIntent {
    data class DoSignUp(val email: String, val password: String, val confirmPassword: String) : SignUpIntent()
}