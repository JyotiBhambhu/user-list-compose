package com.jyoti.user.contacts.domain.use_case

import com.jyoti.user.contacts.domain.mapper.ContactsMapper
import com.jyoti.user.contacts.domain.repo.ContactsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsUseCase @Inject constructor(
    private val contactMapper: ContactsMapper,
    private val contactsRepo: ContactsRepo,
) {

    operator fun invoke(
        page: Int
    ) = contactsRepo.listUsers(
        page
    ).map {
        contactMapper.mapSealedResult(it)
    }
}
