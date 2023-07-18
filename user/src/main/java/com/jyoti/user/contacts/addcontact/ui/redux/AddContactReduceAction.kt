package com.jyoti.user.contacts.addcontact.ui.redux

import com.jyoti.core.base.ReduceAction
import com.jyoti.user.contacts.addcontact.ui.model.AddContactUiModel

sealed class AddContactReduceAction : ReduceAction {
    data class NameError(val isError: Boolean) : AddContactReduceAction()
    data class JobError(val isError: Boolean) : AddContactReduceAction()
    object CreateUserStarted : AddContactReduceAction()
    data class CreateUserError(val message: String) : AddContactReduceAction()
    data class CreateUserSuccess(val uiModel: AddContactUiModel) : AddContactReduceAction()
}