package com.jyoti.user.domain

import com.jyoti.core.network.SealedResult
import com.jyoti.user.contacts.domain.mapper.AddContactMapper
import com.jyoti.user.data.FakeContactsRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddContactUseCaseTest {
    private lateinit var addContactMapper: AddContactMapper
    private lateinit var contactsRepo: FakeContactsRepo

    @Before
    fun setUp() {
        contactsRepo = FakeContactsRepo()
        addContactMapper = AddContactMapper()
    }

    @Test
    fun `validate AddContact`() = runBlocking {
        runBlocking {
            val sealedResult = contactsRepo.addUser(name = "Jyoti", job = "Engineer").map {
                addContactMapper.mapSealedResult(it)
            }.first()
            assert((sealedResult as SealedResult.Response).response?.id == "1")
        }

    }

}