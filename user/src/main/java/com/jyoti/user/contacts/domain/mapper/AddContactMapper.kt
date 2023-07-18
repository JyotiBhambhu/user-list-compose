package com.jyoti.user.contacts.domain.mapper

import com.jyoti.core.base.BaseMapper
import com.jyoti.user.contacts.addcontact.ui.model.AddContactUiModel
import com.jyoti.user.contacts.data.model.AddContactResponseModel
import javax.inject.Inject

class AddContactMapper @Inject constructor(): BaseMapper<AddContactResponseModel, AddContactUiModel>() {

    override fun mapper(inValue: AddContactResponseModel?): AddContactUiModel? =
        inValue?.let { data ->
            AddContactUiModel(
                id = data.id ?: "",
                name = data.name ?: "",
                job = data.job ?: ""
            )
        }
}
