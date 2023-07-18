package com.jyoti.auth.login.ui.redux

import com.jyoti.core.base.LoadState
import com.jyoti.core.base.State
import com.jyoti.core.util.Event
import com.jyoti.core.util.toEvent

data class LoginViewState(
    val emailError: Boolean,
    val passwordError: Boolean,
    val loginError: String,
    val loadState: LoadState,
    val navigateToHome: Event<Boolean>,
) : State {
    companion object {
        val initial = LoginViewState(
            emailError = false,
            passwordError = false,
            loginError = "",
            navigateToHome = false.toEvent(),
            loadState = LoadState.IDLE
        )
    }
}

