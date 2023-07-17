package com.jyoti.auth.login.ui.redux

import com.jyoti.core.base.LoadState
import com.jyoti.core.base.State
import com.jyoti.core.util.Event
import com.jyoti.core.util.toEvent

data class LoginViewState(
    val emailError: String?,
    val passwordError: String?,
    val loginError: String?,
    val loadState: LoadState,
    val navigateToHome: Event<Boolean>?,
) : State {
    companion object {
        val initial = LoginViewState(
            emailError = null,
            passwordError = null,
            loginError = null,
            navigateToHome = false.toEvent(),
            loadState = LoadState.IDLE
        )
    }
}

