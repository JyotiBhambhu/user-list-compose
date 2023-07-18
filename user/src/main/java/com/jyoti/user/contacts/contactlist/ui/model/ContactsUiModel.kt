package com.jyoti.user.contacts.contactlist.ui.model

import com.jyoti.core.base.BaseResponseModel

data class ContactsUiModel(
    val page: Int = 0,
    val totalPages: Int = 0,
    val data: List<Contact> = listOf(),
) : BaseResponseModel<ContactsUiModel>() {
    data class Contact(
        val id: Int = 0,
        val email: String = "",
        val name: String = "",
        val url: String = "",
    )
}
