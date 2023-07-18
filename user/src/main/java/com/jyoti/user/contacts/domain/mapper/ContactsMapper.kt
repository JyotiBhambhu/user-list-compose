package com.jyoti.user.contacts.domain.mapper

import com.jyoti.core.base.BaseMapper
import com.jyoti.user.contacts.contactlist.ui.model.ContactsUiModel
import com.jyoti.user.contacts.data.model.ContactsResponseModel
import javax.inject.Inject

class ContactsMapper @Inject constructor() :
    BaseMapper<ContactsResponseModel, ContactsUiModel>() {

    override fun mapper(inValue: ContactsResponseModel?): ContactsUiModel? =
        inValue?.let { contacts ->
            ContactsUiModel(
                page = contacts.page ?: 0,
                totalPages = contacts.totalPages ?: 0,
                data = mutableListOf<ContactsUiModel.Contact>().apply {
                    contacts.data?.forEach { contact ->
                        add(
                            ContactsUiModel.Contact(
                                id = contact.id ?: 0,
                                email = contact.email ?: "",
                                name = contact.fName + contact.lName,
                                url = contact.url ?: ""
                            )
                        )
                    }
                }
            )
        }
}
