package com.jyoti.user.contacts.domain.mapper

import com.jyoti.core.base.BaseMapper
import com.jyoti.user.contacts.data.model.ContactsResponseModel
import javax.inject.Inject

class ContactsMapper @Inject constructor() :
    BaseMapper<ContactsResponseModel, ContactsResponseModel>() {

    override fun mapper(inValue: ContactsResponseModel?): ContactsResponseModel? =
        inValue
}
