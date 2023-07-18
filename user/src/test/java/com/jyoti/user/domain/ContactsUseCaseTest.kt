package com.jyoti.user.domain

import com.jyoti.core.network.SealedResult
import com.jyoti.user.contacts.domain.mapper.ContactsMapper
import com.jyoti.user.data.FakeContactsRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ContactsUseCaseTest {
    private lateinit var contactMapper: ContactsMapper
    private lateinit var contactsRepo: FakeContactsRepo

    @Before
    fun setUp() {
        contactsRepo = FakeContactsRepo()
        contactMapper = ContactsMapper()
    }

    @Test
    fun `validate AddContact`() = runBlocking {
        runBlocking {
            val sealedResult = contactsRepo.listUsers(page = 1).map {
                contactMapper.mapSealedResult(it)
            }.first()
            assert((sealedResult as SealedResult.Response).response?.data?.size == 1)
        }
    }

}