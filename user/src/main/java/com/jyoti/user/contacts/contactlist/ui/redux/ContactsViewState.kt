package com.jyoti.user.contacts.contactlist.ui.redux

import com.jyoti.core.base.LoadState
import com.jyoti.core.base.State
import com.jyoti.user.contacts.contactlist.ui.model.ContactsUiModel

data class ContactsViewState(
    val loadState: LoadState,
    val contacts: List<ContactsUiModel.Contact>,
    val selectedId: Int,
    val page: Int,
    val totalPages: Int,
    val loadMore: Boolean,
    val fetchUserError: String,
): State{

    companion object {
        val initial = ContactsViewState(
            contacts = listOf(),
            page = 1,
            totalPages = 1,
            loadState = LoadState.IDLE,
            fetchUserError = "",
            selectedId = -1,
            loadMore = false
        )
    }
}
