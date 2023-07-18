package com.jyoti.user.contacts.domain.repo

import com.jyoti.core.network.SealedResult
import com.jyoti.user.contacts.data.model.AddContactResponseModel
import com.jyoti.user.contacts.data.model.ContactsResponseModel
import kotlinx.coroutines.flow.Flow

interface ContactsRepo {
    fun listUsers(page: Int): Flow<SealedResult<ContactsResponseModel?>>
    fun addUser(name: String, job: String): Flow<SealedResult<AddContactResponseModel?>>
}