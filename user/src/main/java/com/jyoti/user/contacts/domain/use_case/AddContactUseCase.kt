package com.jyoti.user.contacts.domain.use_case

import com.jyoti.user.contacts.domain.mapper.AddContactMapper
import com.jyoti.user.contacts.domain.repo.ContactsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val addContactMapper: AddContactMapper,
    private val contactsRepo: ContactsRepo,
) {

    operator fun invoke(
        name: String,
        job: String,
    ) = contactsRepo.addUser(
        name,
        job
    ).map {
        addContactMapper.mapSealedResult(it)
    }
}
