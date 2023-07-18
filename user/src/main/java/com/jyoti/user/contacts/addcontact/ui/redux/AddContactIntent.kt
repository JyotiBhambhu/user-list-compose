package com.jyoti.user.contacts.addcontact.ui.redux

import com.jyoti.core.base.MviIntent

sealed class AddContactIntent : MviIntent {
    data class createUser(val name: String, val job: String) : AddContactIntent()
}