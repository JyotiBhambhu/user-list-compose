package com.jyoti.user.contacts.contactlist.ui.redux

import com.jyoti.core.base.MviIntent

sealed class ContactsIntent : MviIntent {
    data class FetchUsers(val page: Int) : ContactsIntent()
    data class SelectContact(val id: Int) : ContactsIntent()
}