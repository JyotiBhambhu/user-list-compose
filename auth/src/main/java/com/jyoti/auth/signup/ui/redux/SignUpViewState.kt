package com.jyoti.auth.signup.ui.redux

import com.jyoti.core.base.LoadState
import com.jyoti.core.base.State
import com.jyoti.core.util.Event
import com.jyoti.core.util.toEvent

data class SignUpViewState(
    val emailError: Boolean,
    val passwordError: Boolean,
    val confirmPasswordError: Boolean,
    val signUpError: String,
    val loadState: LoadState,
    val navigateUp: Event<Boolean>,
) : State {
    companion object {
        val initial = SignUpViewState(
            emailError = false,
            passwordError = false,
            confirmPasswordError = false,
            signUpError = "",
            navigateUp = false.toEvent(),
            loadState = LoadState.IDLE
        )
    }
}

