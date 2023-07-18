package com.jyoti.user.contacts.contactlist.ui.redux

import com.jyoti.core.base.ReduceAction
import com.jyoti.user.contacts.addcontact.ui.model.AddContactUiModel
import com.jyoti.user.contacts.contactlist.ui.model.ContactsUiModel

sealed class ContactsReduceAction : ReduceAction {
    data class ContactSelected(val id: Int) : ContactsReduceAction()
    object FetchUserStarted : ContactsReduceAction()
    data class FetchUserError(val message: String) : ContactsReduceAction()
    data class FetchUserSuccess(val uiModel: ContactsUiModel) : ContactsReduceAction()
}