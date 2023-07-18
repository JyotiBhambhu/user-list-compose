package com.jyoti.user.contacts.addcontact.ui.redux

import com.jyoti.core.base.LoadState
import com.jyoti.core.base.State
import com.jyoti.core.util.Event
import com.jyoti.core.util.toEvent

data class AddContactViewState(
    val nameError: Boolean,
    val jobError: Boolean,
    val addUserError: String,
    val loadState: LoadState,
    val navigateUp: Event<Boolean>,
    val name: String
) : State {
    companion object {
        val initial = AddContactViewState(
            nameError = false,
            jobError = false,
            addUserError = "",
            navigateUp = false.toEvent(),
            loadState = LoadState.IDLE,
            name = ""
        )
    }
}